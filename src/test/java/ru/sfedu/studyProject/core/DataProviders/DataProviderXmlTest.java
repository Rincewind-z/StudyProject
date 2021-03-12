package ru.sfedu.studyProject.core.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.studyProject.core.enums.*;
import ru.sfedu.studyProject.core.model.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderXmlTest {
    private static final Logger log = LogManager.getLogger(DataProviderXmlTest.class);
    private static final DataProvider dataProvider = new DataProviderXml();

    private static final long userId = 0;



    @BeforeAll
    static void init() {

        DataProviderXml dataProviderXml = new DataProviderXml();
        DataProviderXml.deleteAll();
        DataProviderXml.createFiles();
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
        List<Material> listMaterial = dataProvider.getMaterial(userId);
        Assertions.assertFalse(listMaterial.isEmpty());
        Assertions.assertEquals("TestMaterial1", listMaterial.get(0).getName());
    }

    @Test
    @Order(1)
    void getMaterialFailed() {
        List<Material> listMaterial = dataProvider.getMaterial(8);
        Assertions.assertTrue(listMaterial.isEmpty());
    }

    @Test
    @Order(9)
    void deleteMaterialSuccess() {
        Assertions.assertTrue(dataProvider.deleteMaterial(userId, 1));
    }

    @Test
    @Order(9)
    void deleteMaterialFailed() {
        Assertions.assertFalse(dataProvider.deleteMaterial(8, 1));
        Assertions.assertFalse(dataProvider.deleteMaterial(userId, 8));
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
        optionalMaterial = materialList.stream()
                .filter(desiredMaterial -> desiredMaterial.getId() == material.getId() && material.getUserId() == userId)
                .findAny();
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

        optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        material = optionalMaterial.get();
        material.setId(6);
        Assertions.assertFalse(dataProvider.editMaterial(userId, material));
    }

    @Test
    @Order(1)
    public void createFursuitPartSuccess() throws Exception {
        Optional<Project> optionalProject = dataProvider.getProject(userId)
                .stream()
                .filter(project -> project.getProjectType().equals(ProjectType.FURSUIT))
                .findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Assertions.assertTrue(dataProvider.createFursuitPart(userId, optionalProject.get().getId(),
                "Fursuit part name1", 5000));
        Assertions.assertTrue(dataProvider.createFursuitPart(userId, optionalProject.get().getId(),
                "Fursuit part name2", 2500));

    }

    @Order(1)
    @Test
    public void createFursuitPartFailed() throws Exception {
        Assertions.assertFalse(dataProvider.createFursuitPart(userId, 0, null, 2500));
    }

    @Test
    @Order(2)
    void getFursuitPartSuccess() {
        List<FursuitPart> listFursuitPart = dataProvider.getFursuitPart(userId);
        Assertions.assertFalse(listFursuitPart.isEmpty());
        Assertions.assertEquals("Fursuit part name1", listFursuitPart.get(0).getName());
    }

    @Test
    @Order(2)
    void getFursuitPartFailed() {
        List<FursuitPart> listFursuitPart = dataProvider.getFursuitPart(8);
        Assertions.assertTrue(listFursuitPart.isEmpty());
    }

    @Test
    @Order(9)
    void deleteFursuitPartSuccess() {
        Optional<Project> optionalProject = dataProvider.getProject(userId)
                .stream()
                .filter(project -> project.getProjectType().equals(ProjectType.FURSUIT))
                .findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Fursuit fursuit = (Fursuit) optionalProject.get();
        Assertions.assertTrue(dataProvider.createFursuitPart(fursuit.getUserId(), fursuit.getId(), "deleted part", 2));
        List<Project> projectList = dataProvider.getProject(userId);
        Fursuit finalFursuit = fursuit;
        optionalProject = projectList
                .stream()
                .filter(project -> project.getUserId() == finalFursuit.getUserId() && project.getId() == finalFursuit.getId())
                .findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        fursuit = (Fursuit) optionalProject.get();
        Optional<FursuitPart> optionalFursuitPart = fursuit.getPartList()
                .stream()
                .filter(fursuitPart -> fursuitPart.getName().equals("deleted part"))
                .findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        Assertions.assertTrue(dataProvider.deleteFursuitPart(fursuit.getUserId(),
                fursuit.getId(),
                optionalFursuitPart.get().getId()));
    }

    @Test
    @Order(9)
    void deleteFursuitPartFailed() {
        Assertions.assertFalse(dataProvider.deleteFursuitPart(8, 1, 1));
        Assertions.assertFalse(dataProvider.deleteFursuitPart(userId, 1, 8));
        Assertions.assertFalse(dataProvider.deleteFursuitPart(userId, 8, 1));
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

        optionalFursuitPart = fursuitPartList.stream()
                .filter(listFursuitPart -> listFursuitPart.getUserId() == userId && listFursuitPart.getId() == fursuitPart.getId())
                .findAny();
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

        optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        fursuitPart = optionalFursuitPart.get();
        fursuitPart.setId(6);
        Assertions.assertFalse(dataProvider.editFursuitPart(userId, fursuitPart));
    }

    @Test
    @Order(4)
    void setOutgoingSuccess() {
        List<Material> materialList = dataProvider.getMaterial(userId);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();

        List<Project> projectList = dataProvider.getProject(userId);

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
        List<Material> materialList = dataProvider.getMaterial(userId);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();

        List<Project> projectList = dataProvider.getProject(userId);

        Optional<Project> optToy = projectList.stream()
                .filter(project -> project.getProjectType().equals(ProjectType.TOY))
                .findAny();
        Assertions.assertTrue(optToy.isPresent());
        Assertions.assertFalse(dataProvider.setOutgoing(8585, optToy.get().getId(), material.getId(), 0.5));
    }

    @Test
    @Order(9)
    void  deleteOutgoingSuccess() {
        Optional<Project> optionalProject = dataProvider.getProject(userId).stream()
                .filter(project -> project.getProjectType().equals(ProjectType.TOY))
                .findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Toy toy = (Toy) optionalProject.get();
        Assertions.assertTrue(dataProvider.deleteOutgoing(userId,
                toy.getId(),
                toy.getOutgoings().keySet().stream().findAny().get().getId()));
    }

    @Test
    @Order(9)
    void  deleteOutgoingFailed() {
        Assertions.assertFalse(dataProvider.deleteOutgoing(85, 3, 1));
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
        List<Customer> customerList = dataProvider.getCustomer(userId);
        Assertions.assertFalse(customerList.isEmpty());
        Assertions.assertEquals("Customer name1", customerList.get(0).getName());
    }

    @Test
    @Order(1)
    void getCustomerFailed() {
        List<Customer> customerList = dataProvider.getCustomer(8);
        Assertions.assertTrue(customerList.isEmpty());
    }

    @Test
    @Order(10)
    void deleteCustomerSuccess() {
        Assertions.assertTrue(dataProvider.deleteCustomer(userId, 1));
    }

    @Test
    @Order(9)
    void deleteCustomerFailed() {
        Assertions.assertFalse(dataProvider.deleteCustomer(8, 1));
        Assertions.assertFalse(dataProvider.deleteCustomer(0, 8));
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
        optionalCustomer = customerList.stream()
                .filter(listCustomer -> listCustomer.getUserId() == userId && listCustomer.getId() == customer.getId())
                .findAny();
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

        optionalCustomer = customerList.stream().findAny();
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
        List<Project> projectList = dataProvider.getProject(userId);
        Assertions.assertFalse(projectList.isEmpty());
        Assertions.assertTrue(projectList.stream().anyMatch(project -> project.getName().equals("FurProject name1")));
    }

    @Test
    @Order(5)
    void getProjectFailed() {
        List<Project> projectList = dataProvider.getProject(8);
        Assertions.assertTrue(projectList.isEmpty());
    }

    @Test
    @Order(9)
    void deleteProjectSuccess() {
        Assertions.assertTrue(dataProvider.deleteProject(userId, 1));
    }

    @Test
    @Order(9)
    void deleteProjectFailed() {
        Assertions.assertFalse(dataProvider.deleteProject(8, 0));
        Assertions.assertFalse(dataProvider.deleteProject(userId, 8));
    }

    @Test
    @Order(7)
    void editProjectSuccess() {
        List<Project> projectList = dataProvider.getProject(userId);
        Optional<Project> optionalProject = projectList.stream().findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Project project = optionalProject.get();
        project.setName("Edited name");
        Assertions.assertTrue(dataProvider.editProject(userId, project));
        optionalProject = projectList.stream()
                .filter(listProject -> listProject.getUserId() == userId && listProject.getId() == project.getId())
                .findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Assertions.assertEquals(project, optionalProject.get());
    }

    @Test
    @Order(7)
    void editProjectFailed() {
        List<Project> projectList = dataProvider.getProject(userId);
        Optional<Project> optionalProject = projectList.stream().findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        Project project = optionalProject.get();
        project.setName(null);
        Assertions.assertFalse(dataProvider.editProject(userId, project));

        optionalProject = projectList.stream().findAny();
        Assertions.assertTrue(optionalProject.isPresent());
        project = optionalProject.get();
        project.setId(6);
        Assertions.assertFalse(dataProvider.editProject(userId, project));
    }

    @Test
    @Order(8)
    void getProjectEstimateSuccess() {
        String projectListEstimate = dataProvider.getProjectEstimate(userId);
        Assertions.assertNotNull(projectListEstimate);
        log.info(projectListEstimate);
    }

    @Test
    @Order(8)
    void getProjectEstimateFailed() {
        String projectListEstimate = dataProvider.getProjectEstimate(8);
        Assertions.assertTrue(projectListEstimate.isEmpty());
    }

    @Test
    @Order(8)
    void calculateProjectCostSuccess() {
        double projectCost = dataProvider.calculateProjectCost(userId, 1);
        Assertions.assertTrue(projectCost >= 0);
        log.info(projectCost);
        projectCost = dataProvider.calculateProjectCost(userId, 2);
        Assertions.assertTrue(projectCost >= 0);
        log.info(projectCost);
        projectCost = dataProvider.calculateProjectCost(userId, 3);
        Assertions.assertTrue(projectCost >= 0);
        log.info(projectCost);
    }

    @Test
    @Order(8)
    void calculateProjectCostFailed() {
        dataProvider.calculateProjectCost(userId, 9);
    }
}
