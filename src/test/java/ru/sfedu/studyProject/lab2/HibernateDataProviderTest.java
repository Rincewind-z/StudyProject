package ru.sfedu.studyProject.lab2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HibernateDataProviderTest {

    private static final Logger log = LogManager.getLogger(HibernateDataProviderTest.class);

    @Test
    void createTest() {
        TestEntity testEntity = new TestEntity();
        MyClass myClass = new MyClass();
        myClass.setPole("Polyshko pole");
        testEntity.setName("Name");
        testEntity.setDescription("Description");
        testEntity.setCheck(true);
        testEntity.setPolyshko(myClass);
        testEntity.setDateCreated(new Date(System.currentTimeMillis()));
        HibernateDataProvider.create(testEntity);
        log.debug(testEntity);
        Assertions.assertTrue(testEntity.getId() != 0);
    }

    @Test
    void getTest() {
        TestEntity testEntity = new TestEntity();
        MyClass myClass = new MyClass();
        myClass.setPole("Polyshko pole");
        testEntity.setName("Name");
        testEntity.setDescription("Description");
        testEntity.setCheck(true);
        testEntity.setPolyshko(myClass);
        testEntity.setDateCreated(new Date(System.currentTimeMillis()));
        HibernateDataProvider.create(testEntity);
        log.debug(testEntity);
        Optional<TestEntity> optionalTestEntity = HibernateDataProvider.get(testEntity.getId());
        Assertions.assertTrue(optionalTestEntity.isPresent());
        Assertions.assertEquals(testEntity, optionalTestEntity.get());
    }

    @Test
    void updateTest() {
        TestEntity testEntity = new TestEntity();
        MyClass myClass = new MyClass();
        myClass.setPole("Polyshko pole");
        testEntity.setName("Name");
        testEntity.setDescription("Description");
        testEntity.setCheck(true);
        testEntity.setPolyshko(myClass);
        testEntity.setDateCreated(new Date(System.currentTimeMillis()));
        HibernateDataProvider.create(testEntity);
        testEntity.setName("New name");
        HibernateDataProvider.update(testEntity);
        log.debug(testEntity);
        Optional<TestEntity> optionalTestEntity = HibernateDataProvider.get(testEntity.getId());
        Assertions.assertTrue(optionalTestEntity.isPresent());
        Assertions.assertEquals(testEntity, optionalTestEntity.get());
    }

    @Test
    void deleteTest() {
        TestEntity testEntity = new TestEntity();
        MyClass myClass = new MyClass();
        myClass.setPole("Polyshko pole");
        testEntity.setName("Name");
        testEntity.setDescription("Description");
        testEntity.setCheck(true);
        testEntity.setPolyshko(myClass);
        testEntity.setDateCreated(new Date(System.currentTimeMillis()));
        HibernateDataProvider.create(testEntity);
        log.debug(testEntity);
        Optional<TestEntity> optionalTestEntity = HibernateDataProvider.get(testEntity.getId());
        Assertions.assertTrue(optionalTestEntity.isPresent());
        HibernateDataProvider.delete(testEntity);
        Assertions.assertTrue(HibernateDataProvider.get(testEntity.getId()).isEmpty());
    }
}