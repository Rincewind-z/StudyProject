package ru.sfedu.studyProject.DataProviders;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.studyProject.Constants;
import ru.sfedu.studyProject.enums.MaterialType;
import ru.sfedu.studyProject.enums.Unit;
import ru.sfedu.studyProject.model.FursuitPart;
import ru.sfedu.studyProject.model.Material;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderCsvTest {

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    private static final DataProvider dataProvider = new DataProviderCsv();

    private static <T> void deleteFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION)).delete());
        } catch (IOException e) {
            log.error(e);
        }
    }
    private static void deleteAll() {
        List<Class> classList = new ArrayList<>();
        classList.add(Art.class);
        classList.add(Customer.class);
        classList.add(Fursuit.class);
        classList.add(FursuitPart.class);
        classList.add(Material.class);
        classList.add(Project.class);
        classList.add(Toy.class);
        classList.forEach(DataProviderCsvTest::deleteFile);
    }

    @BeforeAll
    static void init(){
        deleteAll();
    }
    
    @Test
    @Order(0)
    public void testAddMaterialSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.addMaterial(0,
                "TestMaterial1",
                1500,
                Unit.CM,
                MaterialType.FABRIC,
                "TestDescription1",
                200));
        Assertions.assertTrue(dataProvider.addMaterial(1,
                "TestMaterial2",
                1500,
                Unit.CM,
                MaterialType.FABRIC,
                "TestDescription2",
                200));
    }

    @Order(0)
    @Test
    public void testAddMaterialFailed() throws Exception {
        Assertions.assertFalse(dataProvider.addMaterial(0,
                null,
                1500,
                Unit.CM,
                MaterialType.FABRIC,
                "TestDescription",
                200));
    }

    @Test
    @Order(1)
    void getMaterialSuccess(){
        Optional<Material> optionalMaterial = dataProvider.getMaterial(0,0);
        Assertions.assertTrue(optionalMaterial.isPresent());
        Assertions.assertEquals("TestMaterial1", optionalMaterial.get().getName());
    }

    @Test
    @Order(1)
    void getMaterialFailed(){
        Optional<Material> optionalMaterial = dataProvider.getMaterial(0,8);
        Assertions.assertFalse(optionalMaterial.isPresent());

        optionalMaterial = dataProvider.getMaterial(8,0);
        Assertions.assertFalse(optionalMaterial.isPresent());
    }

    @Test
    @Order(1)
    void getMaterialListSuccess(){
        List<Material> listMaterial = dataProvider.getMaterial(0);
        Assertions.assertFalse(listMaterial.isEmpty());
        Assertions.assertEquals("TestMaterial1", listMaterial.get(0).getName());
    }

    @Test
    @Order(1)
    void getMaterialListFailed(){
        List<Material> listMaterial = dataProvider.getMaterial(8);
        Assertions.assertTrue(listMaterial.isEmpty());
    }

    @Test
    @Order(2)
    void  deleteMaterialSuccess() {
        Assertions.assertTrue(dataProvider.deleteMaterial(0,0));
    }

    @Test
    @Order(2)
    void  deleteMaterialFailed() {
        Assertions.assertTrue(dataProvider.deleteMaterial(8,0));
        Assertions.assertTrue(dataProvider.deleteMaterial(0,8));
    }

    @Test
    @Order(2)
    void editMaterialSuccess() {
        List<Material> materialList = dataProvider.getMaterial(1);
        Optional<Material> optionalMaterial = materialList.stream().findAny();
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();
        material.setName("edited name");
        Assertions.assertTrue(dataProvider.editMaterial(1, material));
        optionalMaterial = dataProvider.getMaterial(1,optionalMaterial.get().getId());
        Assertions.assertTrue(optionalMaterial.isPresent());
        Assertions.assertEquals(material, optionalMaterial.get());
    }

    @Test
    @Order(2)
    void editMaterialFailed() {
        Optional<Material> optionalMaterial = dataProvider.getMaterial(1,0);
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();
        material.setName(null);
        Assertions.assertFalse(dataProvider.editMaterial(1, material));

        optionalMaterial = dataProvider.getMaterial(1,1);
        Assertions.assertTrue(optionalMaterial.isPresent());
        material = optionalMaterial.get();
        material.setId(6);
        Assertions.assertFalse(dataProvider.editMaterial(1, material));
    }

    @Test
    @Order(0)
    public void createFursuitPartSuccess() throws Exception {
        Assertions.assertTrue(dataProvider.createFursuitPart(0,
                "Fursuit part name1"));
        Assertions.assertTrue(dataProvider.createFursuitPart(1,
                "Fursuit part name2"));
    }

    @Order(0)
    @Test
    public void createFursuitPartFailed() throws Exception {
        Assertions.assertFalse(dataProvider.createFursuitPart(0, null));
    }

    @Test
    @Order(1)
    void getFursuitPartSuccess(){
        Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(0,0);
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        Assertions.assertEquals("Fursuit part name1", optionalFursuitPart.get().getName());
    }

    @Test
    @Order(1)
    void getFursuitPartFailed(){
        Optional<FursuitPart> optionalFursuitPart = dataProvider.getFursuitPart(0,8);
        Assertions.assertFalse(optionalFursuitPart.isPresent());

        optionalFursuitPart = dataProvider.getFursuitPart(8,0);
        Assertions.assertFalse(optionalFursuitPart.isPresent());
    }

    @Test
    @Order(1)
    void getFursuitPartListSuccess(){
        List<FursuitPart> listFursuitPart = dataProvider.getFursuitPart(0);
        Assertions.assertFalse(listFursuitPart.isEmpty());
        Assertions.assertEquals("Fursuit part name1", listFursuitPart.get(0).getName());
    }

    @Test
    @Order(1)
    void getFursuitPartListFailed(){
        List<FursuitPart> listFursuitPart = dataProvider.getFursuitPart(8);
        Assertions.assertTrue(listFursuitPart.isEmpty());
    }

    @Test
    @Order(2)
    void deleteFursuitPartSuccess() {
        Assertions.assertTrue(dataProvider.deleteFursuitPart(0,0));
    }

    @Test
    @Order(2)
    void deleteFursuitPartFailed() {
        Assertions.assertTrue(dataProvider.deleteFursuitPart(8,0));
        Assertions.assertTrue(dataProvider.deleteFursuitPart(0,8));
    }

    @Test
    @Order(2)
    void editFursuitPartSuccess() {
        List<FursuitPart> fursuitPartList = dataProvider.getFursuitPart(1);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        FursuitPart fursuitPart = optionalFursuitPart.get();
        fursuitPart.setName("Edited part name");
        Assertions.assertTrue(dataProvider.editFursuitPart(1, fursuitPart));
        optionalFursuitPart = dataProvider.getFursuitPart(1,0);
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        Assertions.assertEquals(fursuitPart, optionalFursuitPart.get());
    }

    @Test
    @Order(2)
    void editFursuitPartFailed() {
        List<FursuitPart> fursuitPartList = dataProvider.getFursuitPart(1);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream().findAny();
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        FursuitPart fursuitPart = optionalFursuitPart.get();
        fursuitPart.setName(null);
        Assertions.assertFalse(dataProvider.editFursuitPart(1, fursuitPart));

        optionalFursuitPart = dataProvider.getFursuitPart(1,0);
        Assertions.assertTrue(optionalFursuitPart.isPresent());
        fursuitPart = optionalFursuitPart.get();
        fursuitPart.setId(6);
        Assertions.assertFalse(dataProvider.editFursuitPart(1, fursuitPart));
    }
}