package ru.sfedu.studyProject.DataProviders;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.sfedu.studyProject.enums.MaterialType;
import ru.sfedu.studyProject.enums.Unit;
import ru.sfedu.studyProject.model.Material;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderCsvTest {

    private static final Logger log = LogManager.getLogger(DataProviderCsvTest.class);
    private static final DataProvider dataProvider = new DataProviderCsv();

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
        Optional<Material> optionalMaterial = dataProvider.getMaterial(1,0);
        Assertions.assertTrue(optionalMaterial.isPresent());
        Material material = optionalMaterial.get();
        material.setName("edited name");
        Assertions.assertTrue(dataProvider.editMaterial(1, material));
        optionalMaterial = dataProvider.getMaterial(1,0);
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

        optionalMaterial = dataProvider.getMaterial(1,0);
        Assertions.assertTrue(optionalMaterial.isPresent());
        material = optionalMaterial.get();
        material.setId(6);
        Assertions.assertFalse(dataProvider.editMaterial(1, material));
    }
}