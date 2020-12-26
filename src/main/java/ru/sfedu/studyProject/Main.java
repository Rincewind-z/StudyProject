package ru.sfedu.studyProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.DataProviders.DataProvider;
import ru.sfedu.studyProject.DataProviders.DataProviderCsv;
import ru.sfedu.studyProject.DataProviders.DataProviderJdbc;
import ru.sfedu.studyProject.DataProviders.DataProviderXml;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.Customer;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Main {
  private static final Logger log = LogManager.getLogger(Main.class);

  private static DataProvider getDataProvider(String msg) throws IOException {
    switch (msg) {
      case Constants.DATA_PROVIDER_CSV:
        DataProviderCsv.createAll();
        return new DataProviderCsv();
      case Constants.DATA_PROVIDER_XML:
        DataProviderXml.createFiles();
        return new DataProviderXml();
      case Constants.DATA_PROVIDER_JDBC:
        DataProviderJdbc.getInstance().dropDB();
        DataProviderJdbc.getInstance().setDB();
        return DataProviderJdbc.getInstance();
    }
    throw new NullPointerException(ConfigurationUtil.getConfigurationEntry(Constants.WRONG_DATA));
  }

  //java -jar ... DataProviderCsv createCustomer 1 name vk.com/** 88005553535

  //"DataProviderCsv" [0] = args[0]
  //"<Имя функции>" [1]  = args[1]
  //"<параметр 1>" [2] = args[2]
  //"<параметр 2>" [3] = args[3]
  //"<параметр 3>" [4] = args[4]
  //...

  private static void createProjects(DataProvider dataProvider) {
    dataProvider.createCustomer(0, "Ivan", "vk.com...", "+7 999 ...");
    dataProvider.createMaterial(0, "PD", 1500, Unit.RUNNING_METER, MaterialType.FABRIC, "Best of the best", 2.5F);
    dataProvider.createMaterial(0, "ds", 3000, Unit.KG, MaterialType.FILLER, "Super", 10);
    dataProvider.createMaterial(0, "Work", 1500, Unit.MAN_HOURS, MaterialType.WORK, "Plak plak", 4);
    dataProvider.createProject(0, "Fursuit project", new Date(16080399000000L), 1, FursuitType.MINI_PARTIAL, FursuitStyle.TOONY, PaymentType.FIFTY_FIFTY);
    dataProvider.createProject(0, "Art project", new Date(16080399000000L), 1, ArtType.HEADSHOT, ArtStyle.STANDARD, 300, PaymentType.FULL_PAYMENT);
    dataProvider.createProject(0, "Toy project", new Date(16080399000000L), 1, ToyType.FUR_TREE, ToyStyle.MIXED, PaymentType.FULL_PAYMENT);
    dataProvider.createFursuitPart(0, 1, "Head");
    dataProvider.createFursuitPart(0, 1, "Paws");
    dataProvider.createFursuitPart(0, 1, "Tail");
    dataProvider.setOutgoing(0, 1, 1, 1, 0.5);
    dataProvider.setOutgoing(0, 1, 1, 3, 30);
    dataProvider.setOutgoing(0, 1, 2, 1, 0.2);
    dataProvider.setOutgoing(0, 1, 3, 2, 0.6);
    dataProvider.setOutgoing(0, 3, 1, 0.2);
  }


  public static void main(String[] args) {
    try {
      if (args.length < 2) {
        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_NOT_ENOUGH_INPUT_VARS));
        return;
      }
      DataProvider dataProvider = getDataProvider(args[0]);
      createProjects(dataProvider);
      if (args[1].equals(ConfigurationUtil.getConfigurationEntry(Constants.METHOD_NAME_CREATE_CUSTOMER))) {
        if (args.length < 6) {
          log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_NOT_ENOUGH_INPUT_VARS));
          return;
        }
        if (dataProvider.createCustomer(Long.parseLong(args[2]), args[3], args[4], args[5])) {
          System.out.println(ConfigurationUtil.getConfigurationEntry(Constants.MSG_CUSTOMER_CREATE_SUCCESS));
        }
      }
      if (args[1].equals(ConfigurationUtil.getConfigurationEntry(Constants.METHOD_NAME_GET_PROJECT_ESTIMATE))) {
        if (args.length < 3) {
          log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_NOT_ENOUGH_INPUT_VARS));
        } else if (args.length == 3) {
          System.out.println(dataProvider.getProjectEstimate(Long.parseLong(args[2])));
        } else if (args.length == 4) {
          System.out.println(dataProvider.getProjectEstimate(Long.parseLong(args[2]), Long.parseLong(args[3])));
        } else {
          log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_TOO_MANY_VARS));
        }
      }
      if (args[1].equals(ConfigurationUtil.getConfigurationEntry(Constants.METHOD_NAME_GET_CUSTOMER))) {
        if (args.length < 3) {
          log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_NOT_ENOUGH_INPUT_VARS));
        } else if (args.length == 3) {
          List<Customer> customerList = dataProvider.getCustomer(Long.parseLong(args[2]));
          customerList.forEach(System.out::println);
        } else if (args.length == 4) {
          Optional<Customer> optionalCustomer = dataProvider.getCustomer(Long.parseLong(args[2]), Long.parseLong(args[3]));
          optionalCustomer.ifPresent(System.out::println);
        } else {
          log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_TOO_MANY_VARS));
        }
      }
    } catch (IOException | NullPointerException | NumberFormatException e) {
      log.error(e);
    }
  }
}