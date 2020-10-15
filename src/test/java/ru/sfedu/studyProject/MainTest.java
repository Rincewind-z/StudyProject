package ru.sfedu.studyProject;

import junit.framework.TestCase;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Random;

public class MainTest extends TestCase {
  private static final Logger log = LogManager.getLogger(Main.class);


  public void testLogger() throws IOException {
    log.info(ConfigurationUtil.getConfigurationEntry(Constants.MAIN_TEST_MESSAGE));
  }

  public void testLevels() throws IOException {
        log.info(Level.OFF);
        log.info(Level.FATAL);
        log.info(Level.ERROR);
        log.info(Level.WARN);
        log.info(Level.INFO);
        log.info(Level.DEBUG);
        log.info(Level.TRACE);
        log.info(Level.ALL);
  }


  public void testString() throws IOException {

    Random random = new Random();

    int bound =  Integer.parseInt(ConfigurationUtil.getConfigurationEntry(Constants.FORMAT_BOUND));

    log.info(String.format(ConfigurationUtil.getConfigurationEntry(Constants.FORMAT_STRING),
            random.nextInt(bound),
            random.nextInt(bound)));
  }

}