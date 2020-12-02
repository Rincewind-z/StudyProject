package ru.sfedu.studyProject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.IOException;

public class Main {
  private static final Logger log = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws IOException {
    log.info(ConfigurationUtil.getConfigurationEntry(Constants.MAIN_TEST_MESSAGE));
    log.info(ConfigurationUtil.getConfigurationEntry(Constants.FORMAT_BOUND));
  }
}
