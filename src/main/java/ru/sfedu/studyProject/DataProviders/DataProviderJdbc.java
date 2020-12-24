package ru.sfedu.studyProject.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.Constants;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class DataProviderJdbc implements DataProvider {
  private static final Logger log = LogManager.getLogger(DataProviderJdbc.class);
  private static final DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
  private static DataProviderJdbc instance;
  private Connection connection;

  private DataProviderJdbc() {
    connect();
  }

  public static DataProviderJdbc getInstance() {
    if (instance == null) {
      instance = new DataProviderJdbc();
    }
    return instance;
  }

  //
  private void connect() {
    try {
      Class.forName("org.h2.Driver");
      //connection = DriverManager.getConnection("jdbc:h2:file:Z:\\projects\\asya\\src\\main\\resources\\data\\db");
      connection = DriverManager.getConnection("jdbc:h2:mem:calculator");
    } catch (ClassNotFoundException | SQLException e) {
      log.error(e);
    }
  }

  private boolean executesRequest(String request) {
    try {
      log.debug(request);
      Statement statement = connection.createStatement();
      statement.executeUpdate(request);
      statement.close();
      connection.commit();
      return true;
    } catch (SQLException throwables) {
      log.error(throwables);
      return false;
    }
  }

  public boolean setDB(){
    //language=H2
    return executesRequest("create table if not exists CUSTOMER( ID LONG auto_increment, USER_ID LONG not null, DATE_OF_CREATION TIMESTAMP default CURRENT_TIME not null, NAME VARCHAR not null, URL VARCHAR not null, PHONE_NUMBER VARCHAR not null, constraint CUSTOMER_PK primary key (ID));")
            && executesRequest("create table if not exists MATERIAL( ID LONG auto_increment, USER_ID LONG not null, DATE_OF_CREATION TIMESTAMP default CURRENT_TIME not null, MATERIAL_NAME VARCHAR not null, MATERIAL_TYPE INT not null, COST FLOAT not null, DESCRIPTION VARCHAR not null, UNIT INT not null, IN_STOCK FLOAT not null, constraint MATERIAL_PK primary key (ID));")
            && executesRequest("create table if not exists ART( ID LONG auto_increment, USER_ID LONG not null, CUSTOMER LONG not null, DATE_OF_CREATION TIMESTAMP default CURRENT_TIME not null, DEADLINE TIMESTAMP not null, NAME VARCHAR not null, PROGRESS FLOAT not null, PAYMENT_TYPE INT not null, PROJECT_TYPE INT not null, ART_TYPE INT not null, ART_STYLE INT not null, COST DOUBLE not null, constraint ART_PK primary key (ID), constraint ART_CUSTOMER_ID_FK foreign key (CUSTOMER) references CUSTOMER (ID) on update cascade on delete cascade);")
            && executesRequest("create table if not exists FURSUIT( ID LONG auto_increment, USER_ID LONG not null, CUSTOMER LONG not null, DATE_OF_CREATION TIMESTAMP default CURRENT_TIME not null, DEADLINE TIMESTAMP not null, NAME VARCHAR not null, PROGRESS FLOAT not null, PAYMENT_TYPE INT not null, PROJECT_TYPE INT not null, FURSUIT_TYPE INT not null, FURSUIT_STYLE INT not null, constraint FURSUIT_PK primary key (ID), constraint FURSUIT_CUSTOMER_ID_FK foreign key (CUSTOMER) references CUSTOMER (ID) on update cascade on delete cascade);")
            && executesRequest("create table if not exists FURSUIT_PART( ID LONG auto_increment, USER_ID LONG not null, DATE_OF_CREATION TIMESTAMP default CURRENT_TIME not null, NAME VARCHAR, PROGRESS FLOAT not null, PROJECT_ID LONG not null, constraint FURSUIT_PART_PK primary key (ID), constraint FURSUIT_PART_FURSUIT_ID_FK foreign key (PROJECT_ID) references FURSUIT (ID) on update cascade on delete cascade);")
            && executesRequest("create table if not exists FURSUIT_PART_OUTGOINGS( FURSUIT_PART_ID LONG not null, MATERIAL_ID LONG not null, AMOUNT DOUBLE not null, constraint FURSUIT_PART_OUTGOINGS_PK primary key (FURSUIT_PART_ID, MATERIAL_ID), constraint FURSUIT_PART_OUTGOINGS_FURSUIT_PART_ID_FK foreign key (FURSUIT_PART_ID) references FURSUIT_PART (ID) on update cascade on delete cascade, constraint FURSUIT_PART_OUTGOINGS_MATERIAL_ID_FK foreign key (MATERIAL_ID) references MATERIAL (ID) on update cascade on delete cascade);")
            && executesRequest("create table if not exists TOY (  ID LONG auto_increment,  USER_ID LONG not null,  CUSTOMER LONG not null,  DATE_OF_CREATION TIMESTAMP default CURRENT_TIME not null,  DEADLINE TIMESTAMP not null,  NAME VARCHAR not null,  PROGRESS FLOAT not null,  PAYMENT_TYPE INT,  PROJECT_TYPE INT not null,  TOY_STYLE INT not null,  TOY_TYPE INT not null,  constraint TOY_PK  primary key (ID),  constraint TOY_CUSTOMER_ID_FK  foreign key (CUSTOMER) references CUSTOMER (ID) on update cascade on delete cascade ); ")
            && executesRequest("create table if not exists TOY_OUTGOINGS( TOY_ID LONG not null, MATERIAL_ID LONG not null, AMOUNT DOUBLE not null, constraint TOY_OUTGOINGS_PK primary key (TOY_ID, MATERIAL_ID), constraint TOY_OUTGOINGS_MATERIAL_ID_FK foreign key (MATERIAL_ID) references MATERIAL (ID) on update cascade on delete cascade, constraint TOY_OUTGOINGS_TOY_ID_FK foreign key (TOY_ID) references TOY (ID) on update cascade on delete cascade);");
  }

  public boolean dropDB(){
    return executesRequest("drop table if exists CUSTOMER;")
            && executesRequest("drop table if exists MATERIAL;")
            && executesRequest("drop table if exists ART;")
            && executesRequest("drop table if exists FURSUIT;")
            && executesRequest("drop table if exists FURSUIT_PART;")
            && executesRequest("drop table if exists FURSUIT_PART_OUTGOINGS;")
            && executesRequest("drop table if exists TOY;")
            && executesRequest("drop table if exists TOY_OUTGOINGS;");
  }

  private long getNextProjectId(){
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT max(id) as MAX_ID FROM ((SELECT id from ART) union (select id from TOY) union (select id from FURSUIT));");
      if(resultSet.next()){
        return resultSet.getLong("MAX_ID") + 1;
      }
      return 1;
    } catch (SQLException e) {
      log.error(e);
      return -1;
    }
  }

  @Override
  public boolean createMaterial(long userId,
                                String materialName,
                                float cost,
                                Unit unit,
                                MaterialType materialType,
                                String description,
                                float inStock) {
    try {
      if (materialName == null || unit == null || materialType == null || description == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH,"INSERT INTO PUBLIC.MATERIAL (USER_ID, MATERIAL_NAME, MATERIAL_TYPE, COST, DESCRIPTION, UNIT, IN_STOCK) VALUES (%d, '%s', %d, %.2f, '%s', %d, %.2f)",
              userId,
              materialName,
              materialType.ordinal(),
              cost,
              description,
              unit.ordinal(),
              inStock
              ));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  @Override
  public boolean editMaterial(long userId, Material editMaterial) {
    try {
      if (editMaterial == null ||
              editMaterial.getMaterialType() == null
              || editMaterial.getName() == null
              || editMaterial.getDateOfCreation() == null
              || editMaterial.getDescription() == null
              || editMaterial.getUnit() == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      if(getMaterial(editMaterial.getUserId(), editMaterial.getId()).isEmpty()){
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_MATERIAL_NOT_FOUNDED));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "UPDATE PUBLIC.MATERIAL t SET t.MATERIAL_NAME = '%s', t.MATERIAL_TYPE = %d, t.COST = %.2f, t.DESCRIPTION = '%s', t.UNIT = %d, t.IN_STOCK = %.2f WHERE t.ID = %d and t.USER_ID = %d",
              editMaterial.getName(),
              editMaterial.getMaterialType().ordinal(),
              editMaterial.getCost(),
              editMaterial.getDescription(),
              editMaterial.getUnit().ordinal(),
              editMaterial.getInStock(),
              editMaterial.getId(),
              editMaterial.getUserId()
      ));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  @Override
  public boolean deleteMaterial(long userId, long id) {
    return executesRequest(String.format("delete from MATERIAL where USER_ID = %d and ID = %d",
            userId,
            id));
  }

  private Material setMaterial(ResultSet resultSet) {
    try {
      Material material = new Material();
      material.setId(resultSet.getLong("ID"));
      material.setUserId(resultSet.getLong("USER_ID"));
      material.setName(resultSet.getString("MATERIAL_NAME"));
      material.setDateOfCreation(resultSet.getTimestamp("DATE_OF_CREATION"));
      material.setCost(resultSet.getFloat("COST"));
      material.setDescription(resultSet.getString("DESCRIPTION"));
      material.setInStock(resultSet.getFloat("IN_STOCK"));
      material.setMaterialType(MaterialType.values()[resultSet.getInt("MATERIAL_TYPE")]);
      material.setUnit(Unit.values()[resultSet.getInt("UNIT")]);
      return material;
    } catch (SQLException e) {
      log.error(e);
      return new Material();
    }
  }

  @Override
  public Optional<Material> getMaterial(long userId, long id){
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("Select * from MATERIAL where USER_ID = %d and ID = %d",
              userId,
              id));
      if (!resultSet.next()){
        return Optional.empty();
      }
      Material material = setMaterial(resultSet);
      statement.close();
      return Optional.of(material);
    } catch (SQLException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  @Override
  public List<Material> getMaterial(long userId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("Select * from MATERIAL where USER_ID = %d",
              userId));
      List<Material> materialList = new ArrayList<>();
      while (resultSet.next()) {
        materialList.add(setMaterial(resultSet));
      }
      statement.close();
      return materialList;
    } catch (SQLException e) {
      log.error(e);
      return new ArrayList<>();
    }
  }

  @Override
  public boolean createCustomer(long userId, String name, String url, String phoneNumber) {
    try {
      if (name == null || url == null || phoneNumber == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "INSERT INTO PUBLIC.CUSTOMER (USER_ID, NAME, URL, PHONE_NUMBER) VALUES (%d, '%s', '%s', '%s')",
      userId,
      name,
      url,
      phoneNumber));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  @Override
  public boolean editCustomer(long userId, Customer editCustomer) {
    try {
      if (editCustomer == null ||
              editCustomer.getName() == null ||
              editCustomer.getDateOfCreation() == null ||
              editCustomer.getUrl() == null ||
              editCustomer.getPhoneNumber() == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      if (getCustomer(editCustomer.getUserId(), editCustomer.getId()).isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_CUSTOMER_NOT_FOUNDED));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "UPDATE PUBLIC.CUSTOMER t SET t.NAME = '%s', t.URL = '%s', t.PHONE_NUMBER = '%s' WHERE t.ID = %d and t.USER_ID = %d",
              editCustomer.getName(),
              editCustomer.getUrl(),
              editCustomer.getPhoneNumber(),
              editCustomer.getId(),
              editCustomer.getUserId()));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  @Override
  public boolean deleteCustomer(long userId, long customerId) {
    return executesRequest(String.format("delete from CUSTOMER where USER_ID = %d and ID = %d",
            userId,
            customerId));
  }

  private Customer setCustomer(ResultSet resultSet){
    try {
      Customer customer = new Customer();
      customer.setId(resultSet.getLong("ID"));
      customer.setUserId(resultSet.getLong("USER_ID"));
      customer.setName(resultSet.getString("NAME"));
      customer.setDateOfCreation(resultSet.getTimestamp("DATE_OF_CREATION"));
      customer.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
      customer.setUrl(resultSet.getString("URL"));
      return customer;
    } catch (SQLException e) {
      log.error(e);
      return new Customer();
    }
  }

  @Override
  public Optional<Customer> getCustomer(long userId, long customerId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM CUSTOMER WHERE USER_ID = %d and ID = %d",
              userId,
              customerId));
      if (!resultSet.next()){
        return Optional.empty();
      }
      Customer customer = setCustomer(resultSet);
      statement.close();
      return Optional.of(customer);
    } catch (SQLException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  @Override
  public List<Customer> getCustomer(long userId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("Select * from CUSTOMER where USER_ID = %d",
              userId));
      List<Customer> customerList = new ArrayList<>();
      while (resultSet.next()) {
        customerList.add(setCustomer(resultSet));
      }
      statement.close();
      return customerList;
    } catch (SQLException e) {
      log.error(e);
      return new ArrayList<>();
    }
  }

  @Override
  public boolean createProject(long userId,
                               String projectName,
                               Date deadline,
                               long customerId,
                               FursuitType fursuitType,
                               FursuitStyle fursuitStyle,
                               PaymentType paymentType) {
    try {
      Date date = new Date(System.currentTimeMillis());
      if (projectName == null
              || deadline.before(date)
              || fursuitType == null
              || fursuitStyle == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "INSERT INTO PUBLIC.FURSUIT (ID, USER_ID, CUSTOMER, DEADLINE, NAME, PROGRESS, PAYMENT_TYPE, PROJECT_TYPE, FURSUIT_TYPE, FURSUIT_STYLE) VALUES (%d, %d, %d, parsedatetime ('%s', '%s'), '%s', 0, %d, %d, %d, %d)",
              getNextProjectId(),
              userId,
              customerId,
              dateFormat.format(deadline),
              Constants.DATE_FORMAT,
              projectName,
              paymentType.ordinal(),
              ProjectType.FURSUIT.ordinal(),
              fursuitType.ordinal(),
              fursuitStyle.ordinal()));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //INSERT INTO PUBLIC.ART (USER_ID, CUSTOMER, DATE_OF_CREATION, DEADLINE, NAME, PROGRESS, PAYMENT_TYPE, PROJECT_TYPE, ART_TYPE, ART_STYLE) VALUES (2, 2, DEFAULT, '2020-12-23 22:43:02.000000', 'dwad', 0.4, 1, 1, 1, 1)
  @Override
  public boolean createProject(long userId,
                               String projectName,
                               Date deadline,
                               long customerId,
                               ArtType artType,
                               ArtStyle artStyle,
                               double cost,
                               PaymentType paymentType) {
    try {
      Date date = new Date(System.currentTimeMillis());
      if (projectName == null
              || deadline.before(date)
              || artStyle == null
              || artType == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "INSERT INTO PUBLIC.ART (ID, USER_ID, CUSTOMER, DEADLINE, NAME, PROGRESS, PAYMENT_TYPE, PROJECT_TYPE, ART_TYPE, ART_STYLE, COST) VALUES (%d, %d, %d, parsedatetime ('%s', '%s'), '%s', 0, %d, %d, %d, %d, %.2f)",
              getNextProjectId(),
              userId,
              customerId,
              dateFormat.format(deadline),
              Constants.DATE_FORMAT,
              projectName,
              paymentType.ordinal(),
              ProjectType.ART.ordinal(),
              artType.ordinal(),
              artStyle.ordinal(),
              cost));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //INSERT INTO PUBLIC.TOY (USER_ID, CUSTOMER, DATE_OF_CREATION, DEADLINE, NAME, PROGRESS, PAYMENT_TYPE, PROJECT_TYPE, TOY_STYLE, TOY_TYPE) VALUES (2, 2, DEFAULT, '2020-12-23 22:44:07.000000', 'daw', 0.5, 1, 1, 1, 1)
  @Override
  public boolean createProject(long userId,
                               String projectName,
                               Date deadline,
                               long customerId,
                               ToyType toyType,
                               ToyStyle toyStyle,
                               PaymentType paymentType) {
    try {
      Date date = new Date(System.currentTimeMillis());
      if (projectName == null
              || deadline.before(date)
              || toyType == null
              || toyStyle == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "INSERT INTO PUBLIC.TOY (USER_ID, CUSTOMER, DEADLINE, NAME, PROGRESS, PAYMENT_TYPE, PROJECT_TYPE, TOY_STYLE, TOY_TYPE) VALUES (%d, %d, parsedatetime ('%s', '%s'), '%s', 0, %d, %d, %d, %d)",
              userId,
              customerId,
              dateFormat.format(deadline),
              Constants.DATE_FORMAT,
              projectName,
              paymentType.ordinal(),
              ProjectType.TOY.ordinal(),
              toyStyle.ordinal(),
              toyType.ordinal()));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //UPDATE PUBLIC.ART t SET t.CUSTOMER = 2, t.DEADLINE = '2020-12-24 16:55:48.000000', t.NAME = 'dawd', t.PROGRESS = 13, t.PAYMENT_TYPE = 12, t.PROJECT_TYPE = 12, t.ART_TYPE = 12, t.ART_STYLE = 12, t.COST = 1.4 WHERE t.ID = 1 and t.USER_ID = 2
  // UPDATE PUBLIC.FURSUIT t SET t.CUSTOMER = 2, t.DEADLINE = '2479-07-26 17:31:00.000000', t.NAME = 'FurProject 32', t.PROGRESS = 12, t.PAYMENT_TYPE = 12, t.PROJECT_TYPE = 12, t.FURSUIT_TYPE = 12, t.FURSUIT_STYLE = 12 WHERE t.ID = 15 and t.USER_ID = 12
  //UPDATE PUBLIC.TOY t SET t.CUSTOMER = 2, t.NAME = 'aw name1', t.PROGRESS = 21, t.PAYMENT_TYPE = 21, t.PROJECT_TYPE = 21, t.TOY_STYLE = 21, t.TOY_TYPE = 12 WHERE t.ID = 17
  @Override
  public boolean editProject(long userId, Project editedProject) {
    try {
      if (editedProject == null
              || editedProject.getProjectType() == null
              || editedProject.getDateOfCreation() == null
              || editedProject.getName() == null
              || editedProject.getDeadline() == null
              || editedProject.getCustomer() == null
              || editedProject.getPaymentType() == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      Optional<Project> optionalProject = getProject(userId, editedProject.getId());
      if (optionalProject.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
        return false;
      }
      if (!optionalProject.get().getProjectType().equals(editedProject.getProjectType())){
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
        return false;
      }

      switch (optionalProject.get().getProjectType()) {
        case FURSUIT -> {
          Fursuit project = (Fursuit) optionalProject.get();
          Fursuit editedFursuit = (Fursuit) editedProject;
          if (!editedFursuit.getPartList().equals(project.getPartList())) {
            log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
            return false;
          }
          return executesRequest(String.format(Locale.ENGLISH, "UPDATE PUBLIC.FURSUIT t SET t.CUSTOMER = %d, t.DEADLINE = parsedatetime ('%s', '%s'), t.NAME = '%s', t.PROGRESS = %f, t.PAYMENT_TYPE = %d, t.FURSUIT_TYPE = %d, t.FURSUIT_STYLE = %d WHERE t.ID = %d and t.USER_ID = %d",
                  editedFursuit.getCustomer().getId(),
                  dateFormat.format(editedFursuit.getDeadline()),
                  Constants.DATE_FORMAT,
                  editedFursuit.getName(),
                  editedFursuit.getProgress(),
                  editedFursuit.getPaymentType().ordinal(),
                  editedFursuit.getFursuitType().ordinal(),
                  editedFursuit.getFursuitStyle().ordinal(),
                  editedFursuit.getId(),
                  editedFursuit.getUserId()));
        }

        case ART -> {
          Art art = (Art) editedProject;
          return executesRequest(String.format(Locale.ENGLISH, "UPDATE PUBLIC.ART t SET t.CUSTOMER = %d, t.DEADLINE = parsedatetime ('%s', '%s'), t.NAME = '%s', t.PROGRESS = %f, t.PAYMENT_TYPE = %d, t.ART_TYPE = %d, t.ART_STYLE = %d, t.COST = %f WHERE t.ID = %d and t.USER_ID = %d",
                  art.getCustomer().getId(),
                  dateFormat.format(art.getDeadline()),
                  Constants.DATE_FORMAT,
                  art.getName(),
                  art.getProgress(),
                  art.getPaymentType().ordinal(),
                  art.getArtType().ordinal(),
                  art.getArtStyle().ordinal(),
                  art.getCost(),
                  art.getId(),
                  art.getUserId()));
        }

        case TOY -> {
          Toy project = (Toy) optionalProject.get();
          Toy editedToy = (Toy) editedProject;
          if (!editedToy.getOutgoings().equals(project.getOutgoings())) {
            log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
            return false;
          }
          return executesRequest(String.format(Locale.ENGLISH, "UPDATE PUBLIC.TOY t SET t.CUSTOMER = %d, t.DEADLINE = parsedatetime ('%s', '%s'), t.NAME = '%s', t.PROGRESS = %f, t.PAYMENT_TYPE = %d, t.TOY_STYLE = %d, t.TOY_TYPE = %d WHERE t.ID = %d and t.USER_ID = %d",
                  editedToy.getCustomer().getId(),
                  dateFormat.format(editedToy.getDeadline()),
                  Constants.DATE_FORMAT,
                  editedToy.getName(),
                  editedToy.getProgress(),
                  editedToy.getPaymentType().ordinal(),
                  editedToy.getToyStyle().ordinal(),
                  editedToy.getToyType().ordinal(),
                  editedToy.getId(),
                  editedToy.getUserId()));
        }
      }
      return false;
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //getProject(userid, id)
  //DELETE FROM ART WHERE ID = %d and USER_ID = %d
  //DELETE FROM FURSUIT WHERE ID = %d and USER_ID = %d
  //DELETE FROM TOY WHERE ID = %d and USER_ID = %d
  @Override
  public boolean deleteProject(long userId, long projectId) {
    try {
      Optional<Project> optionalProject = getProject(userId, projectId);
      if (optionalProject.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
        return false;
      }
      switch (optionalProject.get().getProjectType()) {
        case FURSUIT -> {
          return executesRequest(String.format("DELETE FROM FURSUIT WHERE USER_ID = %d and ID = %d",
                  userId,
                  projectId));
        }
        case ART -> {
          return executesRequest(String.format("DELETE FROM ART WHERE USER_ID = %d and ID = %d",
                  userId,
                  projectId));
        }
        case TOY -> {
          return executesRequest(String.format("DELETE FROM TOY WHERE USER_ID = %d and ID = %d",
                  userId,
                  projectId));
        }
      }
      return false;
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  private Art setArt(ResultSet resultSet) {
    try {
      Art art = new Art();
      art.setId(resultSet.getLong("ID"));
      art.setUserId(resultSet.getLong("USER_ID"));
      art.setCustomer(getCustomer(art.getUserId(), resultSet.getLong("CUSTOMER")).get());
      art.setDateOfCreation(resultSet.getTimestamp("DATE_OF_CREATION"));
      art.setDeadline(resultSet.getTimestamp("DEADLINE"));
      art.setName(resultSet.getString("NAME"));
      art.setCost(resultSet.getDouble("COST"));
      art.setProgress(resultSet.getFloat("PROGRESS"));
      art.setProjectType(ProjectType.values()[resultSet.getInt("PROJECT_TYPE")]);
      art.setPaymentType(PaymentType.values()[resultSet.getInt("PAYMENT_TYPE")]);
      art.setArtStyle(ArtStyle.values()[resultSet.getInt("ART_STYLE")]);
      art.setArtType(ArtType.values()[resultSet.getInt("ART_TYPE")]);
      return art;
    } catch (SQLException e) {
      log.error(e);
      return new Art();
    }
  }

  private Fursuit setFursuit(ResultSet resultSet) {
    try {
      Fursuit fursuit = new Fursuit();
      fursuit.setId(resultSet.getLong("ID"));
      fursuit.setUserId(resultSet.getLong("USER_ID"));
      fursuit.setCustomer(getCustomer(fursuit.getUserId(), resultSet.getLong("CUSTOMER")).get());
      fursuit.setDateOfCreation(resultSet.getTimestamp("DATE_OF_CREATION"));
      fursuit.setDeadline(resultSet.getTimestamp("DEADLINE"));
      fursuit.setName(resultSet.getString("NAME"));
      fursuit.setPartList(getPartList(fursuit.getUserId(), fursuit.getId()));
      fursuit.setProgress(resultSet.getFloat("PROGRESS"));
      fursuit.setProjectType(ProjectType.values()[resultSet.getInt("PROJECT_TYPE")]);
      fursuit.setPaymentType(PaymentType.values()[resultSet.getInt("PAYMENT_TYPE")]);
      fursuit.setFursuitStyle(FursuitStyle.values()[resultSet.getInt("FURSUIT_STYLE")]);
      fursuit.setFursuitType(FursuitType.values()[resultSet.getInt("FURSUIT_TYPE")]);
      return fursuit;
    } catch (SQLException e) {
      log.error(e);
      return new Fursuit();
    }
  }

  // SELECT * FROM PUBLIC.FURSUIT_PART WHERE PROJECT_ID = 1323
  private List<FursuitPart> getPartList(long userId, long projectId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("Select * from PUBLIC.FURSUIT_PART where USER_ID = %d and PROJECT_ID = %d",
              userId,
              projectId));
      List<FursuitPart> fursuitPartList = new ArrayList<>();
      while (resultSet.next()) {
        fursuitPartList.add(setFursuitPart(resultSet));
      }
      statement.close();
      return fursuitPartList;
    } catch (SQLException e) {
      log.error(e);
      return new ArrayList<>();
    }
  }

  // SELECT MATERIAL.*, AMOUNT FROM MATERIAL INNER JOIN FURSUIT_PART_OUTGOINGS FPO on MATERIAL.ID = FPO.MATERIAL_ID WHERE FPO.FURSUIT_PART_ID = 4
  private Map<Material, Double> getFursuitPartOutgoings(long id) {
    Map<Material, Double> outgoings = new HashMap<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("SELECT MATERIAL.*, AMOUNT FROM MATERIAL INNER JOIN FURSUIT_PART_OUTGOINGS FPO on MATERIAL.ID = FPO.MATERIAL_ID WHERE FPO.FURSUIT_PART_ID = %d",
              id));
      while (resultSet.next()) {
        outgoings.put(setMaterial(resultSet), resultSet.getDouble("AMOUNT"));
      }
      statement.close();
      return outgoings;
    } catch (SQLException e) {
      log.error(e);
      return new HashMap<>();
    }
  }

  //SELECT MATERIAL.*, AMOUNT FROM MATERIAL INNER JOIN TOY_OUTGOINGS TO on MATERIAL.ID = TO.MATERIAL_ID WHERE TO.TOY_ID = 4
  private Map<Material, Double> getToyOutgoings(long id) {
    Map<Material, Double> outgoings = new HashMap<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("SELECT MATERIAL.*, AMOUNT FROM MATERIAL INNER JOIN TOY_OUTGOINGS TO on MATERIAL.ID = TO.MATERIAL_ID WHERE TO.TOY_ID = %d",
              id));
      while (resultSet.next()) {
        outgoings.put(setMaterial(resultSet), resultSet.getDouble("AMOUNT"));
      }
      statement.close();
      return outgoings;
    } catch (SQLException e) {
      log.error(e);
      return new HashMap<>();
    }
  }

  private Toy setToy(ResultSet resultSet) {
    try {
      Toy toy = new Toy();
      toy.setId(resultSet.getLong("ID"));
      toy.setUserId(resultSet.getLong("USER_ID"));
      toy.setCustomer(getCustomer(toy.getUserId(), resultSet.getLong("CUSTOMER")).get());
      toy.setDateOfCreation(resultSet.getTimestamp("DATE_OF_CREATION"));
      toy.setDeadline(resultSet.getTimestamp("DEADLINE"));
      toy.setName(resultSet.getString("NAME"));
      toy.setProgress(resultSet.getFloat("PROGRESS"));
      toy.setProjectType(ProjectType.values()[resultSet.getInt("PROJECT_TYPE")]);
      toy.setPaymentType(PaymentType.values()[resultSet.getInt("PAYMENT_TYPE")]);
      toy.setToyStyle(ToyStyle.values()[resultSet.getInt("TOY_STYLE")]);
      toy.setToyType(ToyType.values()[resultSet.getInt("TOY_TYPE")]);
      toy.setOutgoings(getToyOutgoings(toy.getId()));
      return toy;
    } catch (SQLException e) {
      log.error(e);
      return new Toy();
    }
  }

  //SELECT * FROM ART WHERE ID = %d and USER_ID = %d
  //SELECT * FROM FURSUIT WHERE ID = %d and USER_ID = %d
  //SELECT * FROM TOY WHERE ID = %d and USER_ID = %d
  @Override
  public Optional<Project> getProject(long userId, long projectId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM ART WHERE USER_ID = %d and ID = %d",
              userId,
              projectId));
      if (resultSet.next()) {
        Art art = setArt(resultSet);
        statement.close();
        return Optional.of(art);
      }
      resultSet = statement.executeQuery(String.format("SELECT * FROM FURSUIT WHERE USER_ID = %d and ID = %d",
              userId,
              projectId));
      if (resultSet.next()) {
        Fursuit fursuit = setFursuit(resultSet);
        statement.close();
        return Optional.of(fursuit);
      }
      resultSet = statement.executeQuery(String.format("SELECT * FROM TOY WHERE USER_ID = %d and ID = %d",
              userId,
              projectId));
      if (resultSet.next()) {
        Toy toy = setToy(resultSet);
        statement.close();
        return Optional.of(toy);
      }
      statement.close();
      return Optional.empty();
    } catch (SQLException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  //SELECT * FROM ART WHERE USER_ID = %d
  //SELECT * FROM FURSUIT WHERE USER_ID = %d
  //SELECT * FROM TOY WHERE USER_ID = %d
  @Override
  public List<Project> getProject(long userId) {
    try {
      List<Project> projectList = new ArrayList<>();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM ART WHERE USER_ID = %d",
              userId));
      while (resultSet.next()) {
        Art art = setArt(resultSet);
        projectList.add(art);
      }
      resultSet = statement.executeQuery(String.format("SELECT * FROM FURSUIT WHERE USER_ID = %d",
              userId));
      while (resultSet.next()) {
        Fursuit fursuit = setFursuit(resultSet);
        projectList.add(fursuit);
      }
      resultSet = statement.executeQuery(String.format("SELECT * FROM TOY WHERE USER_ID = %d",
              userId));
      if (resultSet.next()) {
        Toy toy = setToy(resultSet);
        projectList.add(toy);
      }
      statement.close();
      return projectList;
    } catch (SQLException e) {
      log.error(e);
      return new ArrayList<>();
    }
  }

  //INSERT INTO PUBLIC.FURSUIT_PART (USER_ID, DATE_OF_CREATION, NAME, PROGRESS, PROJECT_ID) VALUES (12, DEFAULT, 'awd', 12, 0)
  @Override
  public boolean createFursuitPart(long userId, long fursuitId, String name) {
    try {
      Optional<Project> optionalProject = getProject(userId, fursuitId);
      if (name == null || optionalProject.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }
      if (!optionalProject.get().getProjectType().equals(ProjectType.FURSUIT)) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "INSERT INTO PUBLIC.FURSUIT_PART (USER_ID, NAME, PROGRESS, PROJECT_ID) VALUES (%d, '%s', 0.0, %d)",
              userId,
              name,
              fursuitId));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //UPDATE PUBLIC.FURSUIT_PART t SET t.NAME = 'dd', t.PROGRESS = 323, t.PROJECT_ID = 15 WHERE t.ID = 3 AND t.USER_ID = 123
  @Override
  public boolean editFursuitPart(long userId, FursuitPart editedFursuitPart) {
    try {
      if (editedFursuitPart == null || editedFursuitPart.getName() == null) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
        return false;
      }

      Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, editedFursuitPart.getId());
      if (optFursuitPart.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FURSUIT_PART_NOT_FOUNDED));
        return false;
      }

      if (!editedFursuitPart.getOutgoings().equals(optFursuitPart.get().getOutgoings())) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
        return false;
      }

      return (executesRequest(String.format(Locale.ENGLISH, "Update public.FURSUIT_PART t set t.NAME = '%s', t.PROGRESS = 0.0 where t.ID = %d and t.USER_ID = %d",
              editedFursuitPart.getName(),
              editedFursuitPart.getId(),
              editedFursuitPart.getUserId())));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  // DELETE FROM PUBLIC.FURSUIT_PART WHERE ID = 3 AND USER_ID = 123
  @Override
  public boolean deleteFursuitPart(long userId, long projectId, long partId) {
    try {
      Optional<Project> optionalFursuit = getProject(userId, projectId);
      if (optionalFursuit.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
        return false;
      }
      if (!optionalFursuit.get().getProjectType().equals(ProjectType.FURSUIT)) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
        return false;
      }
      Fursuit fursuit = (Fursuit) optionalFursuit.get();
      if (fursuit.getPartList().stream().noneMatch(fursuitPart -> fursuitPart.getId() == partId)) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
        return false;
      }
      return executesRequest(String.format("DELETE FROM PUBLIC.FURSUIT_PART WHERE ID = %d AND USER_ID = %d AND PROJECT_ID = %d",
              partId,
              userId,
              projectId));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  private FursuitPart setFursuitPart(ResultSet resultSet) {
    try {
      FursuitPart fursuitPart = new FursuitPart();
      fursuitPart.setId(resultSet.getLong("ID"));
      fursuitPart.setUserId(resultSet.getLong("USER_ID"));
      fursuitPart.setName(resultSet.getString("NAME"));
      fursuitPart.setDateOfCreation(resultSet.getTimestamp("DATE_OF_CREATION"));
      fursuitPart.setProgress(resultSet.getFloat("PROGRESS"));
      fursuitPart.setOutgoings(getFursuitPartOutgoings(fursuitPart.getId()));
      return fursuitPart;
    } catch (Exception e) {
      log.error(e);
      return new FursuitPart();
    }
  }

  // SELECT * FROM PUBLIC.FURSUIT_PART WHERE ID = 3 AND USER_ID = 123
  @Override
  public Optional<FursuitPart> getFursuitPart(long userId, long id) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM  PUBLIC.FURSUIT_PART WHERE USER_ID = %d and ID = %d",
              userId,
              id));
      if (resultSet.next()) {
        FursuitPart fursuitPart = setFursuitPart(resultSet);
        statement.close();
        return Optional.of(fursuitPart);
      }
      statement.close();
      return Optional.empty();
    } catch (SQLException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  // SELECT * FROM PUBLIC.FURSUIT_PART WHERE USER_ID = 123
  @Override
  public List<FursuitPart> getFursuitPart (long userId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format("Select * from PUBLIC.FURSUIT_PART where USER_ID = %d",
              userId));
      List<FursuitPart> fursuitPartList = new ArrayList<>();
      while (resultSet.next()) {
        fursuitPartList.add(setFursuitPart(resultSet));
      }
      statement.close();
      return fursuitPartList;
    } catch (SQLException e) {
      log.error(e);
      return new ArrayList<>();
    }
  }

  //MERGE INTO PUBLIC.FURSUIT_PART_OUTGOINGS (FURSUIT_PART_ID, MATERIAL_ID, AMOUNT) VALUES (4, 2, 112)
  @Override
  public boolean setOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId, double amount) {
    try {
      Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, fursuitPartId);
      Optional<Material> optMaterial = getMaterial(userId, materialId);
      if (optFursuitPart.isEmpty() || optMaterial.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "MERGE INTO PUBLIC.FURSUIT_PART_OUTGOINGS (FURSUIT_PART_ID, MATERIAL_ID, AMOUNT) VALUES (%d, %d, %f)",
              fursuitPartId,
              materialId,
              amount));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //MERGE INTO PUBLIC.TOY_OUTGOINGS (TOY_ID, MATERIAL_ID, AMOUNT) VALUES (4, 2, 112)
  @Override
  public boolean setOutgoing(long userId, long toyId, long materialId, double amount) {
    try {
      Optional<Project> optionalToy = getProject(userId, toyId);
      Optional<Material> optMaterial = getMaterial(userId, materialId);
      if (optionalToy.isEmpty() || optMaterial.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
        return false;
      }
      if (!optionalToy.get().getProjectType().equals(ProjectType.TOY)) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
        return false;
      }
      return executesRequest(String.format(Locale.ENGLISH, "MERGE INTO PUBLIC.TOY_OUTGOINGS (TOY_ID, MATERIAL_ID, AMOUNT) VALUES (%d, %d, %f)",
              toyId,
              materialId,
              amount));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //DELETE FROM PUBLIC.TOY_OUTGOINGS WHERE TOY_ID = 4 AND MATERIAL_ID = 2
  @Override
  public boolean deleteOutgoing(long userId, long toyId, long materialId) {
    try {
      Optional<Project> optionalToy = getProject(userId, toyId);
      Optional<Material> optMaterial = getMaterial(userId, materialId);
      if (optionalToy.isEmpty() || optMaterial.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
        return false;
      }
      if (!optionalToy.get().getProjectType().equals(ProjectType.TOY)) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
        return false;
      }
      return executesRequest(String.format("DELETE FROM PUBLIC.TOY_OUTGOINGS WHERE TOY_ID = %d AND MATERIAL_ID = %d",
              toyId,
              materialId));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  //DELETE FROM PUBLIC.FURSUIT_PART_OUTGOINGS WHERE FURSUIT_PART_ID = 4 AND MATERIAL_ID = 2
  @Override
  public boolean deleteOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId) {
    try {
      Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, fursuitPartId);
      Optional<Material> optMaterial = getMaterial(userId, materialId);
      if (optFursuitPart.isEmpty() || optMaterial.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
        return false;
      }
      FursuitPart fursuitPart = optFursuitPart.get();
      Material material = optMaterial.get();
      if (!fursuitPart.getOutgoings().containsKey(material)) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_OUTGOINGS_NOT_FOUNDED));
        return false;
      }
      return executesRequest(String.format("DELETE FROM PUBLIC.FURSUIT_PART_OUTGOINGS WHERE FURSUIT_PART_ID = %d AND MATERIAL_ID = %d",
              fursuitPartId,
              materialId));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  private String customerToString(Customer customer) throws IOException {
    return String.format(ConfigurationUtil.getConfigurationEntry(Constants.CUSTOMER_TO_STRING),
            customer.getName(),
            customer.getUrl(),
            customer.getPhoneNumber());
  }

  private String projectToString(Project project) throws IOException {
    String stringProject = String.format(ConfigurationUtil.getConfigurationEntry(Constants.PROJECT_BASE_TO_STRING),
            project.getProjectType().toString(),
            project.getName(),
            customerToString(project.getCustomer()),
            project.getPaymentType().toString(),
            project.getDateOfCreation().toString(),
            project.getDeadline().toString(),
            project.getProgress() * 100);
    switch (project.getProjectType()) {
      case ART -> {
        Art artProject = (Art) project;
        stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.ART_PROJECT_TO_STRING),
                artProject.getArtType().toString(),
                artProject.getArtStyle().toString(),
                artProject.getCost());
        return stringProject;
      }
      case TOY -> {
        Toy toyProject = (Toy) project;
        stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.TOY_PROJECT_TO_STRING),
                toyProject.getToyType().toString(),
                toyProject.getToyStyle().toString());
      }
      case FURSUIT -> {
        Fursuit fursuitProject = (Fursuit) project;
        stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PROJECT_TO_STRING),
                fursuitProject.getFursuitType().toString(),
                fursuitProject.getFursuitStyle().toString());
      }
    }
    return stringProject;
  }

  private String fursuitPartToString(FursuitPart fursuitPart) throws IOException {
    return String.format(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PART_TO_STRING),
            fursuitPart.getName(),
            fursuitPart.getProgress() * 100,
            fursuitPart.getDateOfCreation().toString());
  }

  private String outgoingsWithCostsToString(Map<Material, Double> outgoingMap, Map<Material, Double> costsMap) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.OUTGOING_TITLES));
    outgoingMap.forEach((material, aDouble) ->
    {
      try {
        stringBuilder.append(String.format(ConfigurationUtil.getConfigurationEntry(Constants.OUTGOINGS_TO_STRING),
                material.getName(),
                material.getCost(),
                aDouble,
                costsMap.get(material)));
      } catch (IOException e) {
        log.error(e);
      }
    });
    return stringBuilder.toString();
  }

  private Map<Material, Double> calculateCosts(Map<Material, Double> outgoingMap) {
    Map<Material, Double> calculatedCosts = new HashMap<>();
    outgoingMap.forEach((material, aDouble) ->
            calculatedCosts.put(material, material.getCost() * aDouble));
    return calculatedCosts;
  }

  @Override
  public String getProjectEstimate(long userId) {
    List<Project> projectList = getProject(userId);
    StringBuilder stringBuilder = new StringBuilder();
    projectList.forEach(project -> stringBuilder.append(getProjectEstimate(userId, project.getId())));
    return stringBuilder.toString();
  }

  @Override
  public String getProjectEstimate(long userId, long projectId) {
    try {
      Optional<Project> project = getProject(userId, projectId);
      if (project.isEmpty()) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
        return "";
      }
      switch (project.get().getProjectType()) {
        case ART -> {
          return projectToString(project.get());
        }
        case TOY -> {
          Toy toyProject = (Toy) project.get();
          Map<Material, Double> costsMap = calculateCosts(toyProject.getOutgoings());
          return projectToString(toyProject) +
                  ConfigurationUtil.getConfigurationEntry(Constants.ESTIMATE_TITLE) +
                  outgoingsWithCostsToString(toyProject.getOutgoings(), costsMap) +
                  ConfigurationUtil.getConfigurationEntry(Constants.AMOUNT_TITLE) +
                  calculateProjectCost(userId, projectId) +
                  ConfigurationUtil.getConfigurationEntry(Constants.NEW_LINE);
        }
        case FURSUIT -> {
          Fursuit fursuitProject = (Fursuit) project.get();
          Map<FursuitPart, Map<Material, Double>> outgoingMapList = new HashMap<>();
          fursuitProject.getPartList().forEach(fursuitPart ->
                  outgoingMapList.put(fursuitPart, calculateCosts(fursuitPart.getOutgoings())));
          StringBuilder estimateBuilder = new StringBuilder();
          estimateBuilder.append(projectToString(fursuitProject));
          estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PARTS_TITLE));
          outgoingMapList.forEach((fursuitPart, costsMap) ->
          {
            try {
              estimateBuilder.append(fursuitPartToString(fursuitPart));
              estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.ESTIMATE_TITLE));
              estimateBuilder.append(outgoingsWithCostsToString(fursuitPart.getOutgoings(), costsMap));
            } catch (IOException e) {
              log.error(e);
            }
          });
          estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.AMOUNT_TITLE));
          estimateBuilder.append(calculateProjectCost(userId, projectId));
          estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.NEW_LINE));
          return estimateBuilder.toString();
        }
      }
      return  "";
    } catch (IOException e) {
      log.error(e);
      return "";
    }
  }

  @Override
  public double calculateProjectCost (long userId, long projectId) {
    try {
      Optional<Project> optionalProject = getProject(userId, projectId);
      if (optionalProject.isEmpty()){
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
        return 0;
      }
      switch (optionalProject.get().getProjectType()){
        case ART -> {
          return ((Art)optionalProject.get()).getCost();
        }
        case TOY -> {
          return calculateCosts(((Toy)optionalProject.get()).getOutgoings()).values().stream()
                  .mapToDouble(value -> value)
                  .sum();
        }
        case FURSUIT -> {
          return ((Fursuit)optionalProject.get())
                  .getPartList()
                  .stream()
                  .mapToDouble(fursuitPart ->
                          calculateCosts(fursuitPart.getOutgoings())
                                  .values()
                                  .stream()
                                  .mapToDouble(value -> value)
                                  .sum())
                  .sum();
        }
      }
      return 0;
    } catch (IOException e) {
      log.error(e);
      return 0;
    }
  }
}
