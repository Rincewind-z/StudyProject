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

    @Test
    void getSchemataTest() {
        Main5Lab.main(new String[]{"lab1", "getSchemata"});
    }

    @Test
    void getSettingsTest() {
        Main5Lab.main(new String[]{"lab1", "getSettings"});
    }

    @Test
    void getTypeInfoTest() {
        Main5Lab.main(new String[]{"lab1", "getTypeInfo"});
    }

    @Test
    void createTestEntityTest() {
        Main5Lab.main(new String[]{"lab2", "createTestEntity", "Entity name", "Entity description", "true", "Polushko pole"});
    }

    @Test
    void getTestEntityTest() {
        Main5Lab.main(new String[]{"lab2", "createTestEntity", "Entity name", "Entity description", "true", "Polushko pole"});
        Main5Lab.main(new String[]{"lab2", "getTestEntity", "1"});
        Main5Lab.main(new String[]{"lab2", "getTestEntity", "2"});
    }

    @Test
    void updateTestEntityTest() {
        Main5Lab.main(new String[]{"lab2", "createTestEntity", "Entity name", "Entity description", "true", "Polushko pole"});
        Main5Lab.main(new String[]{"lab2", "updateEntityTest", "1", "Edited name", "Entity description", "true", "Polushko pole"});
        Main5Lab.main(new String[]{"lab2", "updateEntityTest", "2", "Edited name", "Entity description", "true", "Polushko pole"});
    }
}