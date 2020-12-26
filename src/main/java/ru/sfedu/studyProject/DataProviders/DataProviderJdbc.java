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

  private void connect() {
    try {
      Class.forName(ConfigurationUtil.getConfigurationEntry(Constants.H2_DRIVER));
      connection = DriverManager.getConnection(ConfigurationUtil.getConfigurationEntry(Constants.CONNECTION_URL));
    } catch (ClassNotFoundException | SQLException | IOException e) {
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
    try {
      return executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_CUSTOMER_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_MATERIAL_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_ART_PROJECT_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_FURSUIT_PROJECT_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_FURSUIT_PART_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_FURSUIT_PART_OUTGOINGS_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_TOY_PROJECT_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.CREATE_TOY_PROJECT_OUTGOINGS_TABLE));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  public boolean dropDB() {
    try {
      return executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_TOY_PROJECT_OUTGOINGS_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_TOY_PROJECT_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_FURSUIT_PART_OUTGOINGS_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DTOP_FURSUIT_PART_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_FURSUIT_PROJECT_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_ART_PROJECT_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_MATERIAL_TABLE))
              && executesRequest(ConfigurationUtil.getConfigurationEntry(Constants.DROP_CUSTOMER_TABLE));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  private long getNextProjectId(){
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(ConfigurationUtil.getConfigurationEntry(Constants.REQUEST_NEXT_PROJECT_ID));
      if(resultSet.next()){
        return resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MAX_ID)) + 1;
      }
      return 1;
    } catch (SQLException | IOException e) {
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
      return executesRequest(String.format(Locale.ENGLISH,ConfigurationUtil.getConfigurationEntry(Constants.CREATE_MATERIAL_REQUEST),
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.UPDATE_MATERIAL_REQUEST),
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
    try {
      return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_MATERIAL_REQUEST),
              userId,
              id));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  private Material setMaterial(ResultSet resultSet) {
    try {
      Material material = new Material();
      material.setId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_ID)));
      material.setUserId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_USER_ID)));
      material.setName(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_NAME)));
      material.setDateOfCreation(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_DATE_OF_CREATION)));
      material.setCost(resultSet.getFloat(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_COST)));
      material.setDescription(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_DESCRIPTION)));
      material.setInStock(resultSet.getFloat(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_IN_STOCK)));
      material.setMaterialType(MaterialType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_TYPE))]);
      material.setUnit(Unit.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_MATERIAL_UNIT))]);
      return material;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new Material();
    }
  }

  @Override
  public Optional<Material> getMaterial(long userId, long id){
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_OPTIONAL_MATERIAL_REQUEST),
              userId,
              id));
      if (!resultSet.next()){
        return Optional.empty();
      }
      Material material = setMaterial(resultSet);
      statement.close();
      return Optional.of(material);
    } catch (SQLException | IOException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  @Override
  public List<Material> getMaterial(long userId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_MATERIAL_REQUEST),
              userId));
      List<Material> materialList = new ArrayList<>();
      while (resultSet.next()) {
        materialList.add(setMaterial(resultSet));
      }
      statement.close();
      return materialList;
    } catch (SQLException | IOException e) {
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.CREATE_CUSTOMER_REQUEST),
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.UPDATE_CUSTOMER_REQUEST),
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
    try {
      return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_CUSTOMER_REQUEST),
              userId,
              customerId));
    } catch (IOException e) {
      log.error(e);
      return false;
    }
  }

  private Customer setCustomer(ResultSet resultSet){
    try {
      Customer customer = new Customer();
      customer.setId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_CUSTOMER_ID)));
      customer.setUserId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_CUSTOMER_USER_ID)));
      customer.setName(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_CUSTOMER_NAME)));
      customer.setDateOfCreation(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_CUSTOMER_DATE_OF_CREATION)));
      customer.setPhoneNumber(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_CUSTOMER_PHONE_NUMBER)));
      customer.setUrl(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_CUSTOMER_URL)));
      return customer;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new Customer();
    }
  }

  @Override
  public Optional<Customer> getCustomer(long userId, long customerId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_OPTIONAL_CUSTOMER_REQUEST),
              userId,
              customerId));
      if (!resultSet.next()){
        return Optional.empty();
      }
      Customer customer = setCustomer(resultSet);
      statement.close();
      return Optional.of(customer);
    } catch (SQLException | IOException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  @Override
  public List<Customer> getCustomer(long userId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_CUSTOMER_REQUEST),
              userId));
      List<Customer> customerList = new ArrayList<>();
      while (resultSet.next()) {
        customerList.add(setCustomer(resultSet));
      }
      statement.close();
      return customerList;
    } catch (SQLException | IOException e) {
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.CREATE_FURSUIT_PROJECT_REQUEST),
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.CREATE_ART_PROJECT_REQUEST),
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.CREATE_TOY_PROJECT_REQUEST),
              getNextProjectId(),
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
        case FURSUIT:
          Fursuit project = (Fursuit) optionalProject.get();
          Fursuit editedFursuit = (Fursuit) editedProject;
          if (!editedFursuit.getPartList().equals(project.getPartList())) {
            log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
            return false;
          }
          return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.UPDATE_FURSUIT_PROJECT_REQUEST),
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
        case ART:
          Art art = (Art) editedProject;
          return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.UPDATE_ART_PROJECT_REQUEST),
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
        case TOY:
          Toy toy = (Toy) optionalProject.get();
          Toy editedToy = (Toy) editedProject;
          if (!editedToy.getOutgoings().equals(toy.getOutgoings())) {
            log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
            return false;
          }
          return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.UPDATE_TOY_PROJECT_REQUEST),
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
        case FURSUIT:
          return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_FURSUIT_PROJECT_REQUEST),
                  userId,
                  projectId));
        case ART:
          return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_ART_PROJECT_REQUEST),
                  userId,
                  projectId));
        case TOY:
          return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_TOY_PROJECT_REQUEST),
                  userId,
                  projectId));
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
      art.setId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_ID)));
      art.setUserId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_USER_ID)));
      art.setCustomer(getCustomer(art.getUserId(), resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_CUSTOMER))).get());
      art.setDateOfCreation(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_DATE_OF_CREATION)));
      art.setDeadline(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_DEADLINE)));
      art.setName(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_NAME)));
      art.setCost(resultSet.getDouble(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_COST)));
      art.setProgress(resultSet.getFloat(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_PROGRESS)));
      art.setProjectType(ProjectType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_PROJECT_TYPE))]);
      art.setPaymentType(PaymentType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_PAYMENT_TYPE))]);
      art.setArtStyle(ArtStyle.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_STYLE))]);
      art.setArtType(ArtType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_ART_TYPE))]);
      return art;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new Art();
    }
  }

  private Fursuit setFursuit(ResultSet resultSet) {
    try {
      Fursuit fursuit = new Fursuit();
      fursuit.setId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_ID)));
      fursuit.setUserId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_USER_ID)));
      fursuit.setCustomer(getCustomer(fursuit.getUserId(), resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_CUSTOMER))).get());
      fursuit.setDateOfCreation(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_DATE_OF_CREATION)));
      fursuit.setDeadline(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_DEADLINE)));
      fursuit.setName(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_NAME)));
      fursuit.setPartList(getPartList(fursuit.getUserId(), fursuit.getId()));
      fursuit.setProgress(resultSet.getFloat(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PROGRESS)));
      fursuit.setProjectType(ProjectType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PROJECT_TYPE))]);
      fursuit.setPaymentType(PaymentType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PAYMENT_TYPE))]);
      fursuit.setFursuitStyle(FursuitStyle.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_STYLE))]);
      fursuit.setFursuitType(FursuitType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_TYPE))]);
      return fursuit;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new Fursuit();
    }
  }

  // SELECT * FROM PUBLIC.FURSUIT_PART WHERE PROJECT_ID = 1323
  private List<FursuitPart> getPartList(long userId, long projectId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_FURSUIT_PART_IN_PROJECT_REQUEST),
              userId,
              projectId));
      List<FursuitPart> fursuitPartList = new ArrayList<>();
      while (resultSet.next()) {
        fursuitPartList.add(setFursuitPart(resultSet));
      }
      statement.close();
      return fursuitPartList;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new ArrayList<>();
    }
  }

  // SELECT MATERIAL.*, AMOUNT FROM MATERIAL INNER JOIN FURSUIT_PART_OUTGOINGS FPO on MATERIAL.ID = FPO.MATERIAL_ID WHERE FPO.FURSUIT_PART_ID = 4
  private Map<Material, Double> getFursuitPartOutgoings(long id) {
    Map<Material, Double> outgoings = new HashMap<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_FURSUIT_PART_OUTGOINGS_REQUEST),
              id));
      while (resultSet.next()) {
        outgoings.put(setMaterial(resultSet), resultSet.getDouble(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_AMOUNT)));
      }
      statement.close();
      return outgoings;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new HashMap<>();
    }
  }

  //SELECT MATERIAL.*, AMOUNT FROM MATERIAL INNER JOIN TOY_OUTGOINGS TO on MATERIAL.ID = TO.MATERIAL_ID WHERE TO.TOY_ID = 4
  private Map<Material, Double> getToyOutgoings(long id) {
    Map<Material, Double> outgoings = new HashMap<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_TOY_PROJECT_OUTGOINGS_REQUEST),
              id));
      while (resultSet.next()) {
        outgoings.put(setMaterial(resultSet), resultSet.getDouble(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_AMOUNT)));
      }
      statement.close();
      return outgoings;
    } catch (SQLException | IOException e) {
      log.error(e);
      return new HashMap<>();
    }
  }

  private Toy setToy(ResultSet resultSet) {
    try {
      Toy toy = new Toy();
      toy.setId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_ID)));
      toy.setUserId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_USER_ID)));
      toy.setCustomer(getCustomer(toy.getUserId(), resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_CUSTOMER))).get());
      toy.setDateOfCreation(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_DATE_OF_CREATION)));
      toy.setDeadline(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_DEADLINE)));
      toy.setName(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_NAME)));
      toy.setProgress(resultSet.getFloat(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_PROGRESS)));
      toy.setProjectType(ProjectType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_PROJECT_TYPE))]);
      toy.setPaymentType(PaymentType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_PAYMENT_TYPE))]);
      toy.setToyStyle(ToyStyle.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_STYLE))]);
      toy.setToyType(ToyType.values()[resultSet.getInt(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_TOY_TYPE))]);
      toy.setOutgoings(getToyOutgoings(toy.getId()));
      return toy;
    } catch (SQLException | IOException e) {
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
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_OPTIONAL_ART_PROJECT_REQUEST),
              userId,
              projectId));
      if (resultSet.next()) {
        Art art = setArt(resultSet);
        statement.close();
        return Optional.of(art);
      }
      resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_OPTIONAL_FURSUIT_PROJECT_REQUEST),
              userId,
              projectId));
      if (resultSet.next()) {
        Fursuit fursuit = setFursuit(resultSet);
        statement.close();
        return Optional.of(fursuit);
      }
      resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_OPTIONAL_TOY_PROJECT_REQUEST),
              userId,
              projectId));
      if (resultSet.next()) {
        Toy toy = setToy(resultSet);
        statement.close();
        return Optional.of(toy);
      }
      statement.close();
      return Optional.empty();
    } catch (SQLException | IOException e) {
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
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_ART_PROJECT_REQUEST),
              userId));
      while (resultSet.next()) {
        Art art = setArt(resultSet);
        projectList.add(art);
      }
      resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_FURSUIT_PROJECT_REQUEST),
              userId));
      while (resultSet.next()) {
        Fursuit fursuit = setFursuit(resultSet);
        projectList.add(fursuit);
      }
      resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_TOY_PROJECT_REQUEST),
              userId));
      if (resultSet.next()) {
        Toy toy = setToy(resultSet);
        projectList.add(toy);
      }
      statement.close();
      return projectList;
    } catch (SQLException | IOException e) {
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.CREATE_FURSUIT_PART_REQUEST),
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

      return (executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.UPDATE_FURSUIT_PART_REQUEST),
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
      return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_FURSUIT_PART_REQUEST),
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
      fursuitPart.setId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PART_ID)));
      fursuitPart.setUserId(resultSet.getLong(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PART_USER_ID)));
      fursuitPart.setName(resultSet.getString(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PART_NAME)));
      fursuitPart.setDateOfCreation(resultSet.getTimestamp(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PART_DATE_OF_CREATION)));
      fursuitPart.setProgress(resultSet.getFloat(ConfigurationUtil.getConfigurationEntry(Constants.COLUMN_LABEL_FURSUIT_PART_PROGRESS)));
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
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_OPTIONAL_FURSUIT_PART_REQUEST),
              userId,
              id));
      if (resultSet.next()) {
        FursuitPart fursuitPart = setFursuitPart(resultSet);
        statement.close();
        return Optional.of(fursuitPart);
      }
      statement.close();
      return Optional.empty();
    } catch (SQLException | IOException e) {
      log.error(e);
      return Optional.empty();
    }
  }

  // SELECT * FROM PUBLIC.FURSUIT_PART WHERE USER_ID = 123
  @Override
  public List<FursuitPart> getFursuitPart (long userId) {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(String.format(ConfigurationUtil.getConfigurationEntry(Constants.SELECT_LIST_ALL_FURSUIT_PART_REQUEST),
              userId));
      List<FursuitPart> fursuitPartList = new ArrayList<>();
      while (resultSet.next()) {
        fursuitPartList.add(setFursuitPart(resultSet));
      }
      statement.close();
      return fursuitPartList;
    } catch (SQLException | IOException e) {
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.SET_FURSUIT_PART_OUTGOINGS_REQUEST),
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
      return executesRequest(String.format(Locale.ENGLISH, ConfigurationUtil.getConfigurationEntry(Constants.SET_TOY_PROJECT_OUTGOINGS_REQUEST),
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
      return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_TOY_PROJECT_OUTGOINGS_REQUEST),
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
      return executesRequest(String.format(ConfigurationUtil.getConfigurationEntry(Constants.DELETE_FURSUIT_PART_OUTGOINGS_REQUEST),
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
      case ART:
        Art artProject = (Art) project;
        stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.ART_PROJECT_TO_STRING),
                artProject.getArtType().toString(),
                artProject.getArtStyle().toString(),
                artProject.getCost());
        return stringProject;
      case TOY:
        Toy toyProject = (Toy) project;
        stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.TOY_PROJECT_TO_STRING),
                toyProject.getToyType().toString(),
                toyProject.getToyStyle().toString());
        break;
      case FURSUIT:
        Fursuit fursuitProject = (Fursuit) project;
        stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PROJECT_TO_STRING),
                fursuitProject.getFursuitType().toString(),
                fursuitProject.getFursuitStyle().toString());
        break;
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
        case ART:
          return projectToString(project.get());
        case TOY:
          Toy toyProject = (Toy) project.get();
          Map<Material, Double> costsMapToy = calculateCosts(toyProject.getOutgoings());
          return projectToString(toyProject) +
                  ConfigurationUtil.getConfigurationEntry(Constants.ESTIMATE_TITLE) +
                  outgoingsWithCostsToString(toyProject.getOutgoings(), costsMapToy) +
                  ConfigurationUtil.getConfigurationEntry(Constants.AMOUNT_TITLE) +
                  calculateProjectCost(userId, projectId) +
                  ConfigurationUtil.getConfigurationEntry(Constants.NEW_LINE);
        case FURSUIT:
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
        case ART:
          return ((Art)optionalProject.get()).getCost();
        case TOY:
          return calculateCosts(((Toy)optionalProject.get()).getOutgoings()).values().stream()
                  .mapToDouble(value -> value)
                  .sum();
        case FURSUIT:
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
      return 0;
    } catch (IOException e) {
      log.error(e);
      return 0;
    }
  }
}
