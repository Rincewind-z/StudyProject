package ru.sfedu.studyProject.lab4.util;

import ru.sfedu.studyProject.core.enums.*;
import ru.sfedu.studyProject.lab4.DataProvider;
import ru.sfedu.studyProject.lab4.HibernateDataProvider;
import ru.sfedu.studyProject.lab4.model.*;

import java.util.*;

public class GeneratorDrdr {

    private static final DataProvider dataProvider = new HibernateDataProvider();

    public static List<Project> generateProjects(int amount) {
        List<Project> projectList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int a = random.nextInt(3);
            switch (a) {
                case 0: {
                    Art art = new Art();
                    List<Customer> customerList = generateCustomer(2);
                    customerList.forEach(customer -> dataProvider.createCustomer(customer));
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
                    art.setCustomer(new HashSet<>(customerList));
                    projectList.add(art);
                    break;
                }
                case 1: {
                    Toy toy = new Toy();
                    Map<String, Double> outgoings = new HashMap<>();
                    outgoings.put("Fur", 2.0);
                    outgoings.put("Fleece", 0.5);
                    outgoings.put("Glue", 10.2);
                    List<Customer> customerList = generateCustomer(2);
                    customerList.forEach(customer -> dataProvider.createCustomer(customer));
                    toy.setToyType(ToyType.INTERIOR);
                    toy.setToyStyle(ToyStyle.MIXED);
                    toy.setProjectType(ProjectType.TOY);
                    toy.setDeadline(new Date(16080399000000L));
                    toy.setName("Toy name test");
                    toy.setProgress(0 + random.nextFloat() % 99);
                    toy.setDateOfCreation(new Date(System.currentTimeMillis()));
                    toy.setPaymentType(PaymentType.FULL_PAYMENT);
                    toy.setUserId(1 + random.nextInt() % 3);
                    toy.setOutgoings(outgoings);
                    toy.setCustomer(new HashSet<>(customerList));
                    projectList.add(toy);
                    break;
                }
                case 2: {
                    Fursuit fursuit = new Fursuit();
                    List<String> partList = new ArrayList<>();
                    partList.add("Fursuit part 1");
                    partList.add("Fursuit part 2");
                    partList.add("Fursuit part 3");
                    List<Customer> customerList = generateCustomer(2);
                    customerList.forEach(customer -> dataProvider.createCustomer(customer));
                    fursuit.setFursuitType(FursuitType.MINI_PARTIAL);
                    fursuit.setFursuitStyle(FursuitStyle.TOONY);
                    fursuit.setProjectType(ProjectType.FURSUIT);
                    fursuit.setDeadline(new Date(16080399000000L));
                    fursuit.setName("Fursuit name test");
                    fursuit.setProgress(0 + random.nextFloat() % 99);
                    fursuit.setDateOfCreation(new Date(System.currentTimeMillis()));
                    fursuit.setPaymentType(PaymentType.FULL_PAYMENT);
                    fursuit.setUserId(1 + random.nextInt() % 3);
                    fursuit.setPartList(partList);
                    fursuit.setCustomer(new HashSet<>(customerList));
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
        Set<String> phoneNums = new HashSet<>();
        phoneNums.add("+7-915-***-**-**");
        phoneNums.add("20-18-51");
        for (int i = 0; i < amount; i++) {
            Customer customer = new Customer();
            customer.setName("Customer name");
            customer.setDateOfCreation(new Date(System.currentTimeMillis()));
            customer.setUserId(1 + random.nextInt() % 3);
            customer.setUrl("Customer URL");
            customer.setPhoneNumber(phoneNums);
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
        Map<Material, Double> outgoings = new HashMap<>();
        List<Material> materialList = generateMaterial(2);
        materialList.forEach(material -> {
            dataProvider.createMaterial(material);
            outgoings.put(material, 2.5 + random.nextDouble()%5);
        });
        for (int i = 0; i < amount; i++) {
            FursuitPart fursuitPart = new FursuitPart();
            fursuitPart.setName("Fursuit part name");
            fursuitPart.setProgress(0 + random.nextFloat() % 99);
            fursuitPart.setDateOfCreation(new Date(System.currentTimeMillis()));
            fursuitPart.setOutgoings(outgoings);
            fursuitPart.setUserId(1 + random.nextInt() % 3);
            fursuitPartList.add(fursuitPart);
        }
        return fursuitPartList;
    }
}
