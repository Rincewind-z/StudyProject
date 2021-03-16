package ru.sfedu.studyProject.lab5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class Main5LabTest {

    private static final Logger log = LogManager.getLogger(Main5Lab.class);

    @Test
    void getHelpTest() {
        Main5Lab.main(new String[]{"lab1", "getHelp"});
    }
}