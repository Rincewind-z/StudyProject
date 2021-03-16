package ru.sfedu.studyProject.lab5.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.studyProject.lab5.DataProvider;
import ru.sfedu.studyProject.lab5.HibernateDataProvider;
import ru.sfedu.studyProject.lab5.model.Fursuit;
import ru.sfedu.studyProject.lab5.model.FursuitPart;
import ru.sfedu.studyProject.lab5.model.Project;
import ru.sfedu.studyProject.lab5.util.GeneratorDrdr;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class Lab5Test {

    private static final DataProvider dataProvider = new HibernateDataProvider();
    private static final Logger log = LogManager.getLogger(Lab5Test.class);

    private static Fursuit fursuit;


    @BeforeAll
    static void setUp(){
        fursuit = GeneratorDrdr.generateFurProject();
        dataProvider.createProject(fursuit);
        List<FursuitPart> partList = GeneratorDrdr.generateFursuitPart(3);
        partList.forEach(fursuitPart -> {
            fursuitPart.setFursuit(fursuit);
            dataProvider.createFursuitPart(fursuitPart);
        });
        fursuit.setPartList(new HashSet<>(partList));
        dataProvider.editProject(fursuit);
    }

    @Test
    void nativeSQLTest() {
       DataProviderLab5 dataProviderLab5 = new DPNativeSQL();
       Optional<Fursuit> optFursuit = dataProviderLab5.getFursuit(fursuit.getId());
        Assertions.assertTrue(optFursuit.isPresent());
        log.info(fursuit);
        log.info(optFursuit.get());
        Assertions.assertEquals(fursuit, optFursuit.get());
    }

    @Test
    void criteriaTest() {
        DataProviderLab5 dataProviderLab5 = new DPCriteria();
        Optional<Fursuit> optFursuit = dataProviderLab5.getFursuit(fursuit.getId());
        Assertions.assertTrue(optFursuit.isPresent());
        log.info(fursuit);
        log.info(optFursuit.get());
        Assertions.assertEquals(fursuit, optFursuit.get());
    }

    @Test
    void hqlTest() {
        DataProviderLab5 dataProviderLab5 = new DPHql();
        Optional<Fursuit> optFursuit = dataProviderLab5.getFursuit(fursuit.getId());
        Assertions.assertTrue(optFursuit.isPresent());
        log.info(fursuit);
        log.info(optFursuit.get());
        Assertions.assertEquals(fursuit, optFursuit.get());
    }
}