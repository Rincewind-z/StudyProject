package ru.sfedu.studyProject.lab1;

public class Constants {
    public static final String SELECT_FROM_HELP = "Select TEXT from INFORMATION_SCHEMA.HELP where TOPIC = 'SELECT'";
    public static final String SELECT_FROM_SCHEMATA = "Select * from INFORMATION_SCHEMA.SCHEMATA where ID = 0";
    public static final String SELECT_FROM_SETTINGS = "Select NAME from INFORMATION_SCHEMA.SETTINGS where VALUE = 'false'";
    public static final String SELECT_FROM_TYPE_INFO = "Select TYPE_NAME from INFORMATION_SCHEMA.TYPE_INFO where DATA_TYPE = 1111 and PRECISION = 2147483647";
}
