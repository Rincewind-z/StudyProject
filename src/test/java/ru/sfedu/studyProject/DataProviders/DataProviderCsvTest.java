package ru.sfedu.studyProject.DataProviders;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.studyProject.Constants;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderCsvTest {

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    private static final DataProvider dataProvider = new DataProviderCsv();

    private static final long userId = 0;

    private static <T> void deleteFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION)).delete());
        } catch (IOException e) {
            log.error(e);
        }
    }

    private static <T> void createFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION)).createNewFile());
        } catch (IOException e) {
            log.error(e);
        }
    }

    private static void deleteAndCreateAll() {
        List<Class> classList = new ArrayList<>();
        classList.add(Art.class);
        classList.add(Customer.class);
        classList.add(Fursuit.class);
        classList.add(FursuitPart.class);
        classList.add(Material.class);
        classList.add(Project.class);
        classList.add(Toy.class);
        classList.forEach(DataProviderCsvTest::deleteFile);
        classList.forEach(DataProviderCsvTest::createFile);
    }

    @BeforeAll
    static void init() {
        deleteAndCreateAll();
    }

    @Test
    @Order(0)
    public void createMaterialSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createMaterial(userId,
                "TestMaterial1",
                1500,
                Unit.CM,
                MaterialType.FABRIC,
                "TestDescription1",
                200));
        Assertions.assertTrue(dataProvider.createMaterial(userId,
                "TestMaterial2",
                1500,
                Unit.CM,
                MaterialType.FABRIC,
                "TestDescription2",
                200));
    }

    @Order(0)
    @Test
    public void createMaterialFailed() throws Exception {
        Assertions.assertFalse(dataProvider.createMaterial(userId,
                null,
                1500,
                Unit.CM,
                MaterialType.FABRIC,
                "TestDescription",
                200));
    }

    @Test
    @Order(1)
    void getMaterialSuccess() {
        Optional<Material> optionalMaterial = dataProvider.getMaterial(userId, 1);
        Assertions.assertTrue(optionalMaterial.isPresent());
        Assertions.assertEquals("TestMaterial1", optionalMaterial.get().getName());
    }

    @Test
    @Order(1)
    void getMaterialFailed() {
        Optional<Material> optionalMaterial = dataProvider.getMaterial(userId, 8);
        Assertions.assertFalse(optionalMaterial.isPresent());

        optionalMaterial = dataProvider.getMaterial(8, 0);
        Assertions.assertFalse(optionalMaterial.isPresent());
    }

    @Test
    @Order(1)
    void getMaterialListSuccess() {
        List<Material> listMaterial = dataProvider.getMaterial(userId);
        Assertions.assertFalse(listMaterial.isEmpty());
        Assertions.assertEquals("TestMaterial1", listMaterial.get(0).getName());
    }

    @Test
    @Order(1)
    void getMaterialListFailed() {
        List<Material> listMaterial = dataProvider.getMaterial(8);
        Assertions.assertTrue(listMaterial.isEmpty());
    }

    @Test
    @Order(2)
    void deleteMaterialSuccess() {
        Assertions.assertTrue(dataProvider.deleteMaterial(userId, 0));
    }

    @Test
    @Order(2)
    void deleteMaterialFailed() {
        Assertions.assertTrue(dataProvider.deleteMaterial(8, 0));
        Assertions.assertTrue(dataProvider.deleteMaterial(userId, 8));
    }

    @Test
    @Order(2)
    void editMaterialSuccess() {
        List<Material> materialList = dataProvider.getMaterial(userId);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();
        material.setName("edited name");
        Assertions.assertTrue(dataProvider.editMaterial(userId, material));
        optionalMaterial = dataProvider.getMaterial(userId, optionalMaterial.get().getId());
        Assertions.assertTrue(optionalMaterial.isPresent());
        Assertions.assertEquals(material, optionalMaterial.get());
    }

    @Test
    @Order(2)
    void editMaterialFailed() {
        List<Material> materialList = dataProvider.getMaterial(userId);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();
        material.setName(null);
        Assertions.assertFalse(dataProvider.editMaterial(userId, material));

        optionalMaterial = dataProvider.getMaterial(userId, 1);
        Assertions.assertTrue(optionalMaterial.isPresent());
        material = optionalMaterial.get();
        material.setId(6);
        Assertions.assertFalse(dataProvider.editMaterial(userId, material));
    }

    @Test
    @Order(1)
    public void createFursuitPartSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createFursuitPart(0, 0,
                "Fursuit part name1"));
        Assertions.assertTrue(dataProvider.createFursuitPart(0, 1,
                "Fursuit part name2"));
    }

    @Order(1)
    @Test
    public void createFursuitPartFailed() throws Exception {
        Assertions.assertFalse(dataProvider.createFursuitPart(userId, 0, null));
    }

    @Test
    @Order(2)
    void getFursuitPartSuccess() {
        Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(0, 0);
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        Assertions.assertEquals("Fursuit part name1", optionalFursuitPart.get().getName());
    }

    @Test
    @Order(2)
    void getFursuitPartFailed() {
        Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(userId, 8);
        Assertions.assertFalse(optionalFursuitPart.isPresent());

        optionalFursuitPart = dataProvider.getFursuitPart(8, 0);
        Assertions.assertFalse(optionalFursuitPart.isPresent());
    }

    @Test
    @Order(2)
    void getFursuitPartListSuccess() {
        List<FursuitPart> listFursuitPart = dataProvider.getFursuitPart(userId);
        Assertions.assertFalse(listFursuitPart.isEmpty());
        Assertions.assertEquals("Fursuit part name1", listFursuitPart.get(0).getName());
    }

    @Test
    @Order(2)
    void getFursuitPartListFailed() {
        List<FursuitPart> listFursuitPart = dataProvider.getFursuitPart(8);
        Assertions.assertTrue(listFursuitPart.isEmpty());
    }

    @Test
    @Order(3)
    void deleteFursuitPartSuccess() {
        Assertions.assertTrue(dataProvider.deleteFursuitPart(0, 0,0));
    }

    @Test
    @Order(3)
    void deleteFursuitPartFailed() {
        Assertions.assertFalse(dataProvider.deleteFursuitPart(8, 0, 0));
        Assertions.assertFalse(dataProvider.deleteFursuitPart(userId, 0, 8));
    }

    @Test
    @Order(3)
    void editFursuitPartSuccess() {
        List<FursuitPart> fursuitPartList = dataProvider.getFursuitPart(userId);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        FursuitPart fursuitPart = optionalFursuitPart.get();
        fursuitPart.setName("Edited part name");
        Assertions.assertTrue(dataProvider.editFursuitPart(userId, fursuitPart));
        optionalFursuitPart = dataProvider.getFursuitPart(userId, optionalFursuitPart.get().getId());
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        Assertions.assertEquals(fursuitPart, optionalFursuitPart.get());
    }

    @Test
    @Order(3)
    void editFursuitPartFailed() {
        List<FursuitPart> fursuitPartList = dataProvider.getFursuitPart(userId);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        FursuitPart fursuitPart = optionalFursuitPart.get();
        fursuitPart.setName(null);
        Assertions.assertFalse(dataProvider.editFursuitPart(userId, fursuitPart));

        optionalFursuitPart = dataProvider.getFursuitPart(userId, fursuitPart.getId());
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        fursuitPart = optionalFursuitPart.get();
        fursuitPart.setId(6);
        Assertions.assertFalse(dataProvider.editFursuitPart(userId, fursuitPart));
    }

    @Test
    @Order(4)
    void setOutgoingSuccess() {
        List<FursuitPart> fursuitPartList = dataProvider.getFursuitPart(userId);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        FursuitPart fursuitPart = optionalFursuitPart.get();

        List<Material> materialList = dataProvider.getMaterial(userId);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();

        List<Project> projectList = dataProvider.getProject(userId);
        Optional<Project> optFursuit = projectList.stream()
                .filter(project -> project.getProjectType().equals(ProjectType.FURSUIT))
                .findAny();
        Assertions.assertTrue(optFursuit.isPresent());
        Assertions.assertTrue(dataProvider.setOutgoing(userId, optFursuit.get().getId(), fursuitPart.getId(), material.getId(), 2));
        Assertions.assertTrue(dataProvider.setOutgoing(userId, optFursuit.get().getId(), fursuitPart.getId(), material.getId(), 1.2));

        Optional<Project> optToy = projectList.stream()
                .filter(project -> project.getProjectType().equals(ProjectType.TOY))
                .findAny();
        Assertions.assertTrue(optToy.isPresent());
        Assertions.assertTrue(dataProvider.setOutgoing(userId, optToy.get().getId(), material.getId(), 0.5));
        Assertions.assertTrue(dataProvider.setOutgoing(userId, optToy.get().getId(), material.getId(), 1));
    }

    @Test
    @Order(4)
    void setOutgoingFailed() {
        List<FursuitPart> fursuitPartList = dataProvider.getFursuitPart(userId);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        FursuitPart fursuitPart = optionalFursuitPart.get();

        List<Material> materialList = dataProvider.getMaterial(userId);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();

        Assertions.assertFalse(dataProvider.setOutgoing(9, 1, fursuitPart.getId(), material.getId(), 2));
    }


    @Test
    @Order(9)
    void  deleteOutgoingSuccess() {
        Assertions.assertTrue(dataProvider.deleteOutgoing(0, 3, 1));
        Assertions.assertTrue(dataProvider.deleteOutgoing(0, 1, 1, 1));
    }

    @Test
    @Order(9)
    void  deleteOutgoingFailed() {
        Assertions.assertFalse(dataProvider.deleteOutgoing(8, 3, 1));
        Assertions.assertFalse(dataProvider.deleteOutgoing(userId, 0, 8, 1));
    }

    @Test
    @Order(0)
    public void createCustomerSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createCustomer(userId,
                "Customer name1", "vk.com/id0**", "+7 999 00 **"));
        Assertions.assertTrue(dataProvider.createCustomer(userId,
                "Customer name2", "vk.com/id1**", "+7 999 11 **"));
    }

    @Test
    @Order(0)
    public void createCustomerFailed() throws Exception {
        Assertions.assertFalse(dataProvider.createCustomer(userId, null, "vk.com/id***", "+7 999 ** **"));
    }

    @Test
    @Order(1)
    void getCustomerSuccess() {
        Optional<Customer> optionalCustomer = dataProvider.getCustomer(userId, 1);
        Assertions.assertTrue(optionalCustomer.isPresent());
        Assertions.assertEquals("Customer name1", optionalCustomer.get().getName());
    }

    @Test
    @Order(1)
    void getCustomerFailed() {
        Optional<Customer> optionalCustomer = dataProvider.getCustomer(userId, 8);
        Assertions.assertFalse(optionalCustomer.isPresent());

        optionalCustomer = dataProvider.getCustomer(8, 0);
        Assertions.assertFalse(optionalCustomer.isPresent());
    }

    @Test
    @Order(1)
    void getCustomerListSuccess() {
        List<Customer> customerList = dataProvider.getCustomer(userId);
        Assertions.assertFalse(customerList.isEmpty());
        Assertions.assertEquals("Customer name1", customerList.get(0).getName());
    }

    @Test
    @Order(1)
    void getCustomerListFailed() {
        List<Customer> customerList = dataProvider.getCustomer(8);
        Assertions.assertTrue(customerList.isEmpty());
    }

    @Test
    @Order(2)
    void deleteCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteCustomer(userId, 0));
    }

    @Test
    @Order(2)
    void deleteCustomerFailed() {
        Assertions.assertTrue(dataProvider.deleteCustomer(8, 0));
        Assertions.assertTrue(dataProvider.deleteCustomer(0, 8));
    }

    @Test
    @Order(2)
    void editCustomerSuccess() {
        List<Customer> customerList = dataProvider.getCustomer(userId);
        Optional<Customer> optionalCustomer = customerList.stream().findAny();
        Assertions.assertTrue(optionalCustomer.isPresent());
        Customer customer = optionalCustomer.get();
        customer.setName("Edited customer name");
        Assertions.assertTrue(dataProvider.editCustomer(userId, customer));
        optionalCustomer = dataProvider.getCustomer(userId, optionalCustomer.get().getId());
        Assertions.assertTrue(optionalCustomer.isPresent());
        Assertions.assertEquals(customer, optionalCustomer.get());
    }

    @Test
    @Order(2)
    void editCustomerFailed() {
        List<Customer> customerList = dataProvider.getCustomer(userId);
        Optional<Customer> optionalCustomer = customerList.stream().findAny();
        Assertions.assertTrue(optionalCustomer.isPresent());
        Customer customer = optionalCustomer.get();
        customer.setName(null);
        Assertions.assertFalse(dataProvider.editCustomer(userId, customer));

        optionalCustomer = dataProvider.getCustomer(userId, customer.getId());
        Assertions.assertTrue(optionalCustomer.isPresent());
        customer = optionalCustomer.get();
        customer.setId(6);
        Assertions.assertFalse(dataProvider.editCustomer(userId, customer));
    }

    @Test
    @Order(0)
    void createProjectSuccess() {
        Assertions.assertTrue(dataProvider.createProject(userId,
                "FurProject name1",
                new Date(16080399000000L),
                1,
                FursuitType.MINI_PARTIAL,
                FursuitStyle.TOONY, PaymentType.FIFTY_FIFTY));
        Assertions.assertTrue(dataProvider.createProject(userId,
                "FurProject name2",
                new Date(16080399000000L),
                1,
                FursuitType.DIGIGRADE_FULL,
                FursuitStyle.KEMONO, PaymentType.FIFTY_FIFTY));
        Assertions.assertTrue(dataProvider.createProject(userId,
                "ArtProject name1",
                new Date(16080399000000L),
                1,
                ArtType.HEADSHOT,
                ArtStyle.STANDARD, 500,
                PaymentType.FIFTY_FIFTY));
        Assertions.assertTrue(dataProvider.createProject(userId,
                "ToyProject name1",
                new Date(16080399000000L),
                1,
                ToyType.FUR_TREE,
                ToyStyle.SOLID,
                PaymentType.FIFTY_FIFTY));
    }

    @Test
    @Order(0)
    void createProjectFailed() {
        Assertions.assertFalse(dataProvider.createProject(userId,
                null,
                new Date(16080399000000L),
                1,
                FursuitType.MINI_PARTIAL,
                FursuitStyle.TOONY, PaymentType.FIFTY_FIFTY));
    }

    @Test
    @Order(5)
    void getProjectSuccess() {
        Optional<Project> optionalProject = dataProvider.getProject(userId, 1);
        Assertions.assertFalse(optionalProject.isEmpty());
        Assertions.assertEquals("FurProject name1", optionalProject.get().getName());
    }

    @Test
    @Order(5)
    void getProjectFailed() {
        Optional<Project> optionalProject = dataProvider.getProject(8, 1);
        Assertions.assertFalse(optionalProject.isPresent());

        optionalProject = dataProvider.getProject(userId, 8);
        Assertions.assertFalse(optionalProject.isPresent());
    }

    @Test
    @Order(5)
    void getProjectListSuccess() {
        List<Project> projectList = dataProvider.getProject(userId);
        Assertions.assertFalse(projectList.isEmpty());
        Assertions.assertEquals("FurProject name2", projectList.get(0).getName());
    }

    @Test
    @Order(5)
    void getProjectListFailed() {
        List<Project> projectList = dataProvider.getProject(8);
        Assertions.assertTrue(projectList.isEmpty());
    }

    @Test
    @Order(6)
    void deleteProjectSuccess() {
        Assertions.assertTrue(dataProvider.deleteProject(0, 0));
    }

    @Test
    @Order(6)
    void deleteProjectFailed() {
        Assertions.assertFalse(dataProvider.deleteProject(8, 0));
        Assertions.assertFalse(dataProvider.deleteProject(0, 8));
    }

    @Test
    @Order(7)
    void editProjectSuccess() {
        List<Project> projectList = dataProvider.getProject(0);
        Optional<Project> optionalProject = projectList.stream().findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Project project = optionalProject.get();
        project.setName("Edited name");
        Assertions.assertTrue(dataProvider.editProject(0, project));
        optionalProject = dataProvider.getProject(0, optionalProject.get().getId());
        Assertions.assertTrue(optionalProject.isPresent());
        Assertions.assertEquals(project, optionalProject.get());
    }

    @Test
    @Order(7)
    void editProjectFailed() {
        List<Project> projectList = dataProvider.getProject(0);
        Optional<Project> optionalProject = projectList.stream().findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Project project = optionalProject.get();
        project.setName(null);
        Assertions.assertFalse(dataProvider.editProject(0, project));

        optionalProject = dataProvider.getProject(0, project.getId());
        Assertions.assertTrue(optionalProject.isPresent());
        project = optionalProject.get();
        project.setId(6);
        Assertions.assertFalse(dataProvider.editProject(0, project));
    }

    @Test
    @Order(8)
    void getProjectEstimateSuccess() {
        String singleProjectEstimate = dataProvider.getProjectEstimate(0, 1);
        Assertions.assertNotNull(singleProjectEstimate);
        log.info(singleProjectEstimate);
        singleProjectEstimate = dataProvider.getProjectEstimate(0, 2);
        Assertions.assertNotNull(singleProjectEstimate);
        log.info(singleProjectEstimate);
        singleProjectEstimate = dataProvider.getProjectEstimate(0, 3);
        Assertions.assertNotNull(singleProjectEstimate);
        log.info(singleProjectEstimate);
    }

    @Test
    @Order(8)
    void getProjectEstimateFailed() {
        String singleProjectEstimate = dataProvider.getProjectEstimate(8, 1);
        Assertions.assertTrue(singleProjectEstimate.isEmpty());
    }

    @Test
    @Order(8)
    void getProjectListEstimateSuccess() {
        String projectListEstimate = dataProvider.getProjectEstimate(0);
        Assertions.assertNotNull(projectListEstimate);
        log.info(projectListEstimate);
    }

    @Test
    @Order(8)
    void getProjectListEstimateFailed() {
        String projectListEstimate = dataProvider.getProjectEstimate(8);
        Assertions.assertTrue(projectListEstimate.isEmpty());
    }

    @Test
    @Order(8)
    void calculateProjectCostSuccess() {
        Double projectCost = dataProvider.calculateProjectCost(0, 1);
        log.info(projectCost);
        projectCost = dataProvider.calculateProjectCost(0, 2);
        log.info(projectCost);
        projectCost = dataProvider.calculateProjectCost(0, 3);
        log.info(projectCost);
    }
}