package ru.sfedu.studyProject.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.Customer;
import ru.sfedu.studyProject.model.Material;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderJdbcTest {

  private static final Logger log = LogManager.getLogger(DataProviderJdbcTest.class);
  private static final DataProvider dataProvider = DataProviderJdbc.getInstance();

  @BeforeAll
  static void test(){
    DataProviderJdbc dataProviderJdbc = (DataProviderJdbc) dataProvider;
    Assertions.assertTrue(dataProviderJdbc.setDB());

  }

  @Test
  @Order(0)
  public void createMaterialSuccess() throws Exception {
    Assertions.assertTrue(dataProvider.createMaterial(0,
            "TestMaterial1",
            1500,
            Unit.CM,
            MaterialType.FABRIC,
            "TestDescription1",
            200));
    Assertions.assertTrue(dataProvider.createMaterial(0,
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
    Assertions.assertFalse(dataProvider.createMaterial(0,
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
    Optional<Material> optionalMaterial = dataProvider.getMaterial(0, 1);
    Assertions.assertTrue(optionalMaterial.isPresent());
    Assertions.assertEquals("TestMaterial1", optionalMaterial.get().getName());
  }

  @Test
  @Order(1)
  void getMaterialFailed() {
    Optional<Material> optionalMaterial = dataProvider.getMaterial(0, 8);
    Assertions.assertFalse(optionalMaterial.isPresent());

    optionalMaterial = dataProvider.getMaterial(8, 1);
    Assertions.assertFalse(optionalMaterial.isPresent());
  }

  @Test
  @Order(1)
  void getMaterialListSuccess() {
    List<Material> listMaterial = dataProvider.getMaterial(0);
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
    Assertions.assertTrue(dataProvider.deleteMaterial(0, 1));
  }

  @Test
  @Order(2)
  void deleteMaterialFailed() {
    Assertions.assertTrue(dataProvider.deleteMaterial(8, 0));
    Assertions.assertTrue(dataProvider.deleteMaterial(0, 8));
  }

  @Test
  @Order(2)
  void editMaterialSuccess() {
    List<Material> materialList = dataProvider.getMaterial(0);
    Optional<Material> optionalMaterial = materialList.stream().findAny();
    Assertions.assertTrue(optionalMaterial.isPresent());
    Material material = optionalMaterial.get();
    material.setName("edited name");
    Assertions.assertTrue(dataProvider.editMaterial(0, material));
    optionalMaterial = dataProvider.getMaterial(0, optionalMaterial.get().getId());
    Assertions.assertTrue(optionalMaterial.isPresent());
    Assertions.assertEquals(material, optionalMaterial.get());
  }

  @Test
  @Order(2)
  void editMaterialFailed() {
    List<Material> materialList = dataProvider.getMaterial(0);
    Optional<Material> optionalMaterial = materialList.stream().findAny();
    Assertions.assertTrue(optionalMaterial.isPresent());
    Material material = optionalMaterial.get();
    material.setName(null);
    Assertions.assertFalse(dataProvider.editMaterial(0, material));

    optionalMaterial = dataProvider.getMaterial(0, 1);
    Assertions.assertTrue(optionalMaterial.isPresent());
    material = optionalMaterial.get();
    material.setId(6);
    Assertions.assertFalse(dataProvider.editMaterial(0, material));
  }

  @Test
  @Order(0)
  public void createCustomerSuccess() throws Exception {
    Assertions.assertTrue(dataProvider.createCustomer(0,
            "Customer name1", "vk.com/id0**", "+7 999 00 **"));
    Assertions.assertTrue(dataProvider.createCustomer(0,
            "Customer name2", "vk.com/id1**", "+7 999 11 **"));
  }

  @Test
  @Order(0)
  public void createCustomerFailed() throws Exception {
    Assertions.assertFalse(dataProvider.createCustomer(0, null, "vk.com/id***", "+7 999 ** **"));
  }

  @Test
  @Order(1)
  void getCustomerSuccess() {
    Optional<Customer> optionalCustomer = dataProvider.getCustomer(0, 1);
    Assertions.assertTrue(optionalCustomer.isPresent());
    Assertions.assertEquals("Customer name1", optionalCustomer.get().getName());
  }

  @Test
  @Order(1)
  void getCustomerFailed() {
    Optional<Customer> optionalCustomer = dataProvider.getCustomer(0, 8);
    Assertions.assertFalse(optionalCustomer.isPresent());

    optionalCustomer = dataProvider.getCustomer(8, 1);
    Assertions.assertFalse(optionalCustomer.isPresent());
  }

  @Test
  @Order(1)
  void getCustomerListSuccess() {
    List<Customer> customerList = dataProvider.getCustomer(0);
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
    Assertions.assertTrue(dataProvider.deleteCustomer(0, 1));
  }

  @Test
  @Order(2)
  void deleteCustomerFailed() {
    Assertions.assertTrue(dataProvider.deleteCustomer(8, 1));
    Assertions.assertTrue(dataProvider.deleteCustomer(0, 8));
  }

  @Test
  @Order(2)
  void editCustomerSuccess() {
    List<Customer> customerList = dataProvider.getCustomer(0);
    Optional<Customer> optionalCustomer = customerList.stream().findAny();
    Assertions.assertTrue(optionalCustomer.isPresent());
    Customer customer = optionalCustomer.get();
    customer.setName("Edited customer name");
    Assertions.assertTrue(dataProvider.editCustomer(0, customer));
    optionalCustomer = dataProvider.getCustomer(0, optionalCustomer.get().getId());
    Assertions.assertTrue(optionalCustomer.isPresent());
    Assertions.assertEquals(customer, optionalCustomer.get());
  }

  @Test
  @Order(2)
  void editCustomerFailed() {
    List<Customer> customerList = dataProvider.getCustomer(0);
    Optional<Customer> optionalCustomer = customerList.stream().findAny();
    Assertions.assertTrue(optionalCustomer.isPresent());
    Customer customer = optionalCustomer.get();
    customer.setName(null);
    Assertions.assertFalse(dataProvider.editCustomer(0, customer));

    optionalCustomer = dataProvider.getCustomer(0, customer.getId());
    Assertions.assertTrue(optionalCustomer.isPresent());
    customer = optionalCustomer.get();
    customer.setId(6);
    Assertions.assertFalse(dataProvider.editCustomer(0, customer));
  }

  @Test
  @Order(0)
  void createProjectSuccess() {
    Assertions.assertTrue(dataProvider.createProject(0,
            "FurProject name1",
            new Date(16080399000000L),
            1,
            FursuitType.MINI_PARTIAL,
            FursuitStyle.TOONY, PaymentType.FIFTY_FIFTY));
    Assertions.assertTrue(dataProvider.createProject(0,
            "FurProject name2",
            new Date(16080399000000L),
            1,
            FursuitType.DIGIGRADE_FULL,
            FursuitStyle.KEMONO, PaymentType.FIFTY_FIFTY));
    Assertions.assertTrue(dataProvider.createProject(0,
            "ArtProject name1",
            new Date(16080399000000L),
            1,
            ArtType.HEADSHOT,
            ArtStyle.STANDARD, 500,
            PaymentType.FIFTY_FIFTY));
    Assertions.assertTrue(dataProvider.createProject(0,
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
    Assertions.assertFalse(dataProvider.createProject(0,
            null,
            new Date(16080399000000L),
            1,
            FursuitType.MINI_PARTIAL,
            FursuitStyle.TOONY, PaymentType.FIFTY_FIFTY));
  }
}
