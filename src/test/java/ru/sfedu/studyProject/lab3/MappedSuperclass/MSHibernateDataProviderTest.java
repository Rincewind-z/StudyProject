package ru.sfedu.studyProject.lab3.SingleTable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sfedu.studyProject.lab3.SingleTable.model.*;
import ru.sfedu.studyProject.lab3.SingleTable.util.GeneratorDrdr;

import java.util.List;
import java.util.Optional;


class HibernateDataProviderTest {

    private static final Logger log = LogManager.getLogger(HibernateDataProviderTest.class);
    private static final DataProvider dataProvider = new HibernateDataProvider();

    @Test
    void createMaterialTest() {
        List<Material> materialList = GeneratorDrdr.generateMaterial(3);
        materialList.forEach(material -> {dataProvider.createMaterial(material);
            log.debug(material);
            Assertions.assertTrue(material.getId() != 0);
        });
    }

    @Test
    void getMaterialTest() {
        List<Material> materialList = GeneratorDrdr.generateMaterial(3);
        materialList.forEach(material -> {dataProvider.createMaterial(material);
            Assertions.assertTrue(material.getId() != 0);
            Optional<Material> optionalMaterial = dataProvider.getMaterial(material.getId());
            Assertions.assertTrue(optionalMaterial.isPresent());
            Assertions.assertEquals(material, optionalMaterial.get());
            log.debug(optionalMaterial);
        });
    }

    @Test
    void updateMaterialTest() {
        List<Material> materialList = GeneratorDrdr.generateMaterial(3);
        materialList.forEach(material -> {dataProvider.createMaterial(material);
            Assertions.assertTrue(material.getId() != 0);
            material.setName("New name");
            dataProvider.editMaterial(material);
            log.debug(material);
            Optional<Material> optionalMaterial = dataProvider.getMaterial(material.getId());
            Assertions.assertTrue(optionalMaterial.isPresent());
            Assertions.assertEquals(material, optionalMaterial.get());
        });

    }

    @Test
    void deleteMaterialTest() {
        List<Material> materialList = GeneratorDrdr.generateMaterial(3);
        materialList.forEach(material -> {dataProvider.createMaterial(material);
            log.debug(material);
            Assertions.assertTrue(material.getId() != 0);
            Optional<Material> optionalMaterial = dataProvider.getMaterial(material.getId());
            Assertions.assertTrue(optionalMaterial.isPresent());
            dataProvider.deleteMaterial(material);
            Assertions.assertTrue(dataProvider.getMaterial(material.getId()).isEmpty());
        });
    }

    @Test
    void createProjectTest() {
        List<Project> projectList = GeneratorDrdr.generateProjects(10);
        projectList.forEach(project -> {dataProvider.createProject(project);
            log.debug(project);
            Assertions.assertTrue(project.getId() != 0);
        });
    }

    @Test
    void getProjectTest() {
        List<Project> projectList = GeneratorDrdr.generateProjects(5);
        projectList.forEach(project -> {dataProvider.createProject(project);
            Assertions.assertTrue(project.getId() != 0);
            Optional<Project> optionalProject = dataProvider.getProject(project.getClass(), project.getId());
            Assertions.assertTrue(optionalProject.isPresent());
            Assertions.assertEquals(project, optionalProject.get());
            log.debug(optionalProject);
        });
    }

    @Test
    void updateProjectTest() {
        List<Project> projectList = GeneratorDrdr.generateProjects(5);
        projectList.forEach(project -> {dataProvider.createProject(project);
            Assertions.assertTrue(project.getId() != 0);
            project.setName("New name");
            dataProvider.editProject(project);
            log.debug(project);
            Optional<Project> optionalProject = dataProvider.getProject(project.getClass(), project.getId());
            Assertions.assertTrue(optionalProject.isPresent());
            Assertions.assertEquals(project, optionalProject.get());
        });
    }

    @Test
    void deleteProjectTest() {
        List<Project> projectList = GeneratorDrdr.generateProjects(5);
        projectList.forEach(project -> {dataProvider.createProject(project);
            log.debug(project);
            Assertions.assertTrue(project.getId() != 0);
            Optional<Project> optionalProject = dataProvider.getProject(project.getClass(), project.getId());
            Assertions.assertTrue(optionalProject.isPresent());
            dataProvider.deleteProject(project);
            Assertions.assertTrue(dataProvider.getProject(Fursuit.class, project.getId()).isEmpty());
        });
    }

    @Test
    void createFursuitPartTest() {
        List<FursuitPart> fursuitPartList = GeneratorDrdr.generateFursuitPart(3);
        fursuitPartList.forEach(fursuitPart -> {dataProvider.createFursuitPart(fursuitPart);
            log.debug(fursuitPart);
            Assertions.assertTrue(fursuitPart.getId() != 0);
        });
    }

    @Test
    void getFursuitPartTest() {
        List<FursuitPart> fursuitPartList = GeneratorDrdr.generateFursuitPart(3);
        fursuitPartList.forEach(fursuitPart -> {dataProvider.createFursuitPart(fursuitPart);
            Assertions.assertTrue(fursuitPart.getId() != 0);
            Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(fursuitPart.getId());
            Assertions.assertTrue(optionalFursuitPart.isPresent());
            Assertions.assertEquals(fursuitPart, optionalFursuitPart.get());
            log.debug(optionalFursuitPart);
        });
    }

    @Test
    void updateFursuitPartTest() {
        List<FursuitPart> fursuitPartList = GeneratorDrdr.generateFursuitPart(3);
        fursuitPartList.forEach(fursuitPart -> {dataProvider.createFursuitPart(fursuitPart);
            Assertions.assertTrue(fursuitPart.getId() != 0);
            fursuitPart.setName("New name");
            dataProvider.editFursuitPart(fursuitPart);
            log.debug(fursuitPart);
            Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(fursuitPart.getId());
            Assertions.assertTrue(optionalFursuitPart.isPresent());
            Assertions.assertEquals(fursuitPart, optionalFursuitPart.get());
        });

    }

    @Test
    void deleteFursuitPartTest() {
        List<FursuitPart> fursuitPartList = GeneratorDrdr.generateFursuitPart(3);
        fursuitPartList.forEach(fursuitPart -> {dataProvider.createFursuitPart(fursuitPart);
            log.debug(fursuitPart);
            Assertions.assertTrue(fursuitPart.getId() != 0);
            Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(fursuitPart.getId());
            Assertions.assertTrue(optionalFursuitPart.isPresent());
            dataProvider.deleteFursuitPart(fursuitPart);
            Assertions.assertTrue(dataProvider.getFursuitPart(fursuitPart.getId()).isEmpty());
        });
    }

    @Test
    void createCustomerTest() {
        List<Customer> customerList = GeneratorDrdr.generateCustomer(3);
        customerList.forEach(customer -> {dataProvider.createCustomer(customer);
            log.debug(customer);
            Assertions.assertTrue(customer.getId() != 0);
        });
    }

    @Test
    void getCustomerTest() {
        List<Customer> customerList = GeneratorDrdr.generateCustomer(3);
        customerList.forEach(customer -> {dataProvider.createCustomer(customer);
            Assertions.assertTrue(customer.getId() != 0);
            Optional<Customer> optionalCustomer = dataProvider.getCustomer(customer.getId());
            Assertions.assertTrue(optionalCustomer.isPresent());
            Assertions.assertEquals(customer, optionalCustomer.get());
            log.debug(optionalCustomer);
        });
    }

    @Test
    void updateCustomerTest() {
        List<Customer> customerList = GeneratorDrdr.generateCustomer(3);
        customerList.forEach(customer -> {dataProvider.createCustomer(customer);
            Assertions.assertTrue(customer.getId() != 0);
            customer.setName("New name");
            dataProvider.editCustomer(customer);
            log.debug(customer);
            Optional<Customer> optionalCustomer = dataProvider.getCustomer(customer.getId());
            Assertions.assertTrue(optionalCustomer.isPresent());
            Assertions.assertEquals(customer, optionalCustomer.get());
        });

    }

    @Test
    void deleteCustomerTest() {
        List<Customer> customerList = GeneratorDrdr.generateCustomer(3);
        customerList.forEach(customer -> {dataProvider.createCustomer(customer);
            log.debug(customer);
            Assertions.assertTrue(customer.getId() != 0);
            Optional<Customer> optionalCustomer = dataProvider.getCustomer(customer.getId());
            Assertions.assertTrue(optionalCustomer.isPresent());
            dataProvider.deleteCustomer(customer);
            Assertions.assertTrue(dataProvider.getCustomer(customer.getId()).isEmpty());
        });
    }
}