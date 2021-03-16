package ru.sfedu.studyProject.lab5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.lab1.HibernateDataProvider;
import ru.sfedu.studyProject.lab2.MyClass;
import ru.sfedu.studyProject.lab2.TestEntity;

import java.util.Date;
import java.util.Optional;

public class Main5Lab {
    private static final Logger log = LogManager.getLogger(Main5Lab.class);

    public static void main(String[] args) {
        switch (args[0].toLowerCase()) {
            case "lab1":
                lab1(args);
                break;
            case "lab2":
                lab2(args);
                break;
            case "lab3":
                lab3(args);
                break;
            case "lab4":
                lab4(args);
                break;
            case "lab5":
                lab5(args);
                break;
        }
    }

    private static void lab1(String[] args) {
        switch (args[1]) {
            case "getHelp":
                HibernateDataProvider.getHelp();
                break;
            case "getSchemata":
                HibernateDataProvider.getSchemata();
                break;
            case "getSettings":
                HibernateDataProvider.getSettings();
                break;
            case "getTypeInfo":
                HibernateDataProvider.getTypeInfo();
                break;
        }
    }

    private static void lab2(String[] args) {
        switch (args[1]) {
            case "createTestEntity":
                createTestEntity(args);
                break;
            case "getTestEntity":
                getTestEntity(args);
                break;
            case "updateTestEntity":
                updateTestEntity(args);
                break;
            case "deleteTestEntity":
                deleteTestEntity(args);
                break;
        }
    }

    private static void createTestEntity(String[] args) {
        TestEntity entity = new TestEntity();
        entity.setName(args[2]);
        entity.setDescription(args[3]);
        entity.setDateCreated(new Date(System.currentTimeMillis()));
        entity.setCheck(Boolean.parseBoolean(args[4]));
        MyClass myClass = new MyClass();
        myClass.setPole(args[5]);
        entity.setPolyshko(myClass);
        ru.sfedu.studyProject.lab2.HibernateDataProvider.create(entity);
        log.debug(entity);
        System.out.print(entity);
    }

    private static void getTestEntity(String[] args) {
        Optional<TestEntity> optionalTestEntity = ru.sfedu.studyProject.lab2.HibernateDataProvider.get(Long.parseLong(args[2]));
        optionalTestEntity.ifPresentOrElse(System.out::println, () -> System.out.print("Объект не найден"));
    }

    private static void updateTestEntity(String[] args) {
        TestEntity testEntity = ru.sfedu.studyProject.lab2.HibernateDataProvider.get(Long.parseLong(args[2])).get();
        testEntity.setName(args[3]);
        testEntity.setDescription(args[4]);
        testEntity.setCheck(Boolean.parseBoolean(args[5]));
        MyClass myClass = new MyClass();
        myClass.setPole(args[6]);
        testEntity.setPolyshko(myClass);
        Optional.ofNullable(testEntity).ifPresentOrElse(ru.sfedu.studyProject.lab2.HibernateDataProvider::update, () -> System.out.print("Объект не найден"));
    }

    private static void deleteTestEntity(String[] args) {
        Optional<TestEntity> optionalTestEntity = ru.sfedu.studyProject.lab2.HibernateDataProvider.get(Long.parseLong(args[2]));
        optionalTestEntity.ifPresentOrElse(ru.sfedu.studyProject.lab2.HibernateDataProvider::delete, () -> System.out.print("Объект не найден"));
    }

    private static void lab3(String[] args) {
        switch (args[1]) {
            case "joinedTable":
                joinedTable(args);
                break;
            case "mappedSuperclass":
                mappedSuperclass(args);
                break;
            case "singleTable":
                singleTable(args);
                break;
            case "tablePerClass":
                tablePerClass(args);
                break;
            default:
                System.out.println("Ошибка");
        }
    }

    private static void joinedTable(String[] args) {
        if ("generateProject".equals(args[2])) {
            generateProjectJoinedTable();
        } else {
            System.out.println("Ошибка");
        }
    }

    private static void mappedSuperclass(String[] args) {
        if ("generateProject".equals(args[2])) {
            generateProjectMappedSuperclass();
        } else {
            System.out.println("Ошибка");
        }
    }

    private static void singleTable(String[] args) {
        if ("generateProject".equals(args[2])) {
            generateProjectSingleTable();
        } else {
            System.out.println("Ошибка");
        }
    }

    private static void tablePerClass(String[] args) {
        if ("generateProject".equals(args[2])) {
            generateProjectTablePerClass();
        } else {
            System.out.println("Ошибка");
        }
    }

    private static void generateProjectSingleTable() {
        var projectList = ru.sfedu.studyProject.lab3.SingleTable.util.GeneratorDrdr.generateProjects(10);
        ru.sfedu.studyProject.lab3.SingleTable.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab3.SingleTable.HibernateDataProvider();
        projectList.forEach(dataProvider::createProject);
        projectList.forEach(System.out::println);
    }

    private static void generateProjectJoinedTable() {
        var projectList = ru.sfedu.studyProject.lab3.JoinedTable.util.GeneratorDrdr.generateProjects(10);
        ru.sfedu.studyProject.lab3.JoinedTable.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab3.JoinedTable.HibernateDataProvider();
        projectList.forEach(dataProvider::createProject);
        projectList.forEach(System.out::println);
    }

    private static void generateProjectMappedSuperclass() {
        var projectList = ru.sfedu.studyProject.lab3.MappedSuperclass.util.GeneratorDrdr.generateProjects(10);
        ru.sfedu.studyProject.lab3.MappedSuperclass.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab3.MappedSuperclass.HibernateDataProvider();
        projectList.forEach(dataProvider::createProject);
        projectList.forEach(System.out::println);
    }

    private static void generateProjectTablePerClass() {
        var projectList = ru.sfedu.studyProject.lab3.TablePerClass.util.GeneratorDrdr.generateProjects(10);
        ru.sfedu.studyProject.lab3.TablePerClass.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab3.TablePerClass.HibernateDataProvider();
        projectList.forEach(dataProvider::createProject);
        projectList.forEach(System.out::println);
    }

    private static void lab4(String[] args) {
        switch (args[1]) {
            case "generateCustomer":
                generateCustomer();
                break;
            case "generateFursuitPart":
                generateFursuitPart();
                break;
            case "generateProject":
                generateProject();
                break;
            default:
                System.out.println("Ошибка");
        }
    }

    private static void generateCustomer() {
        var projectList = ru.sfedu.studyProject.lab4.util.GeneratorDrdr.generateCustomer(10);
        ru.sfedu.studyProject.lab4.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab4.HibernateDataProvider();
        projectList.forEach(dataProvider::createCustomer);
        projectList.forEach(System.out::println);
    }

    private static void generateFursuitPart() {
        var projectList = ru.sfedu.studyProject.lab4.util.GeneratorDrdr.generateFursuitPart(10);
        ru.sfedu.studyProject.lab4.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab4.HibernateDataProvider();
        projectList.forEach(dataProvider::createFursuitPart);
        projectList.forEach(System.out::println);
    }

    private static void generateProject() {
        var projectList = ru.sfedu.studyProject.lab4.util.GeneratorDrdr.generateProjects(10);
        ru.sfedu.studyProject.lab4.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab4.HibernateDataProvider();
        projectList.forEach(dataProvider::createProject);
        projectList.forEach(System.out::println);
    }

    private static void lab5(String[] args) {
        if ("generateProject".equals(args[1])) {
            generateProjectLab5();
        } else {
            System.out.println("Ошибка");
        }
    }

    private static void generateProjectLab5() {
        var projectList = ru.sfedu.studyProject.lab5.util.GeneratorDrdr.generateProjects(10);
        ru.sfedu.studyProject.lab5.HibernateDataProvider dataProvider =
                new ru.sfedu.studyProject.lab5.HibernateDataProvider();
        projectList.forEach(dataProvider::createProject);
        projectList.forEach(System.out::println);
    }

}
