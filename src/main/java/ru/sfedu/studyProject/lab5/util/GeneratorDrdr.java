package ru.sfedu.studyProject.lab5.util;

import ru.sfedu.studyProject.core.enums.*;
import ru.sfedu.studyProject.lab5.DataProvider;
import ru.sfedu.studyProject.lab5.HibernateDataProvider;
import ru.sfedu.studyProject.lab5.model.*;

import java.util.*;

public class GeneratorDrdr {

    private static final DataProvider dataProvider = new HibernateDataProvider();

    public static List<Project> generateProjects(int amount) {
        List<Project> projectList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int a = random.nextInt(3);
            Customer customer = GeneratorDrdr.generateCustomer(1).get(0);
            dataProvider.createCustomer(customer);

            switch (a) {
                case 0: {
                    Art art = new Art();
                    art.setArtType(ArtType.FULL_BODY);
                    art.setArtStyle(ArtStyle.CHIBI);
                    art.setProjectType(ProjectType.ART);
                    art.setDeadline(new Date(16080399000000L));
                    art.setName("Art name test");
                    art.setProgress(0 + random.nextFloat() % 99);
                    art.setCost(500 + random.nextFloat() % 1000);
                    art.setDateOfCreation(new Date(System.currentTimeMillis()));
                    art.setPaymentType(PaymentType.FULL_PAYMENT);
                    art.setUserId(1 + random.nextInt() % 3);
                    art.setCustomer(customer);
                    projectList.add(art);
                    break;
                }
                case 1: {
                    Toy toy = new Toy();
                    List<Material> materialSet = generateMaterial(2);
                    materialSet.forEach(material -> dataProvider.createMaterial(material));
                    toy.setOutgoings(new HashSet<>(materialSet));
                    toy.setToyType(ToyType.INTERIOR);
                    toy.setToyStyle(ToyStyle.MIXED);
                    toy.setProjectType(ProjectType.TOY);
                    toy.setDeadline(new Date(16080399000000L));
                    toy.setName("Toy name test");
                    toy.setProgress(0 + random.nextFloat() % 99);
                    toy.setDateOfCreation(new Date(System.currentTimeMillis()));
                    toy.setPaymentType(PaymentType.FULL_PAYMENT);
                    toy.setUserId(1 + random.nextInt() % 3);
                    toy.setCustomer(customer);
                    projectList.add(toy);
                    break;
                }
                case 2: {
                    Fursuit fursuit = new Fursuit();
                    //List<FursuitPart> fursuitPartList = generateFursuitPart(3);
                    //fursuitPartList.forEach(fursuitPart -> dataProvider.createFursuitPart(fursuitPart));
                    fursuit.setFursuitType(FursuitType.MINI_PARTIAL);
                    fursuit.setFursuitStyle(FursuitStyle.TOONY);
                    fursuit.setProjectType(ProjectType.FURSUIT);
                    fursuit.setDeadline(new Date(16080399000000L));
                    fursuit.setName("Fursuit name test");
                    fursuit.setProgress(0 + random.nextFloat() % 99);
                    fursuit.setDateOfCreation(new Date(System.currentTimeMillis()));
                    fursuit.setPaymentType(PaymentType.FULL_PAYMENT);
                    fursuit.setUserId(1 + random.nextInt() % 3);
                    fursuit.setCustomer(customer);
                    //fursuit.setPartList(new HashSet<>(fursuitPartList));
                    projectList.add(fursuit);
                    break;
                }
            }
        }
        return projectList;
    }

    public static List<Customer> generateCustomer(int amount) {
        List<Customer> customerList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            Customer customer = new Customer();
            customer.setName("Customer name");
            customer.setDateOfCreation(new Date(System.currentTimeMillis()));
            customer.setUserId(1 + random.nextInt() % 3);
            customer.setPhoneNumber("*Customer phone num*");
            customerList.add(customer);
        }
        return customerList;
    }

    public static List<Material> generateMaterial(int amount) {
        List<Material> materialList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            Material material = new Material();
            material.setDateOfCreation(new Date(System.currentTimeMillis()));
            material.setMaterialType(MaterialType.FABRIC);
            material.setCost(300F + random.nextFloat() % 5000);
            material.setDescription("Test description");
            material.setName("Fleece");
            material.setInStock(200 + random.nextFloat() % 500);
            material.setUnit(Unit.CM);
            material.setUserId(1 + random.nextInt() % 3);
            materialList.add(material);
        }
        return materialList;
    }

    public static List<FursuitPart> generateFursuitPart(int amount) {
        List<FursuitPart> fursuitPartList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            FurPartDetails furPartDetails = new FurPartDetails();
            furPartDetails.setName("Fursuit part name");
            furPartDetails.setProgress(0 + random.nextFloat() % 99);
            furPartDetails.setCost(5000 + random.nextFloat() % 20000);
            dataProvider.createFPDetails(furPartDetails);
            FursuitPart fursuitPart = new FursuitPart();
            fursuitPart.setDateOfCreation(new Date(System.currentTimeMillis()));
            fursuitPart.setFurPartDetails(furPartDetails);
            fursuitPart.setUserId(1 + random.nextInt() % 3);
            fursuitPartList.add(fursuitPart);
            Fursuit fursuit = generateFurProject();
            dataProvider.createProject(fursuit);
            fursuitPart.setFursuit(fursuit);
        }
        return fursuitPartList;
    }

    public static Fursuit generateFurProject() {
        Fursuit fursuit = new Fursuit();
        Random random = new Random();
        //List<FursuitPart> fursuitPartList = generateFursuitPart(3);
        //fursuitPartList.forEach(fursuitPart -> dataProvider.createFursuitPart(fursuitPart));
        /*Customer customer = generateCustomer(1).get(0);
        dataProvider.createCustomer(customer);
        fursuit.setCustomer(customer);*/
        fursuit.setFursuitType(FursuitType.MINI_PARTIAL);
        fursuit.setFursuitStyle(FursuitStyle.TOONY);
        fursuit.setProjectType(ProjectType.FURSUIT);
        fursuit.setDeadline(new Date(16080399000000L));
        fursuit.setName("Fursuit name test");
        fursuit.setProgress(0 + random.nextFloat() % 99);
        fursuit.setDateOfCreation(new Date(System.currentTimeMillis()));
        fursuit.setPaymentType(PaymentType.FULL_PAYMENT);
        fursuit.setUserId(1 + random.nextInt() % 3);
        //fursuit.setPartList(new HashSet<>(fursuitPartList));
        return fursuit;
    }
}
