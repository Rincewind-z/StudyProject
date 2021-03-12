package ru.sfedu.studyProject.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateDataProviderTest {

    @Test
    void getHelpTest() {
        HibernateDataProvider.getHelp();
    }

    @Test
    void getSchemataTest() {
        HibernateDataProvider.getSchemata();
    }

    @Test
    void getSettingsTest() {
        HibernateDataProvider.getSettings();
    }

    @Test
    void getTypeInfoTest() {
        HibernateDataProvider.getTypeInfo();
    }
}