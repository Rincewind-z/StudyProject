package ru.sfedu.studyProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.DataProviders.DataProviderCsv;
import ru.sfedu.studyProject.enums.MaterialType;
import ru.sfedu.studyProject.enums.Unit;
import ru.sfedu.studyProject.model.Material;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.IOException;
import java.util.Date;

import static ru.sfedu.studyProject.enums.MaterialType.FABRIC;

public class Main {
  private static final Logger log = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    log.info(ConfigurationUtil.getConfigurationEntry(Constants.MAIN_TEST_MESSAGE));
    log.info(ConfigurationUtil.getConfigurationEntry(Constants.FORMAT_BOUND));
  }
}