package ru.sfedu.studyProject.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.studyProject.Constants;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;
import ru.sfedu.studyProject.utils.ConfigurationUtil;
import ru.sfedu.studyProject.utils.XmlWrapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataProviderXml implements DataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderXml.class);

    public DataProviderXml() {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH)).mkdirs());
        } catch (IOException e) {
            log.error(e);
        }
    }

    private static <T> void deleteFile(Class<T> tClass) {
        try {
            log.debug(new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.XML_FILE_EXTENSION)).delete());
        } catch (IOException e) {
            log.error(e);
        }
    }


    public static void deleteAll() {
        List<Class> classList = new ArrayList<>();
        classList.add(Art.class);
        classList.add(Customer.class);
        classList.add(Fursuit.class);
        classList.add(FursuitPart.class);
        classList.add(Material.class);
        classList.add(Project.class);
        classList.add(Toy.class);
        classList.forEach(DataProviderXml::deleteFile);
    }

    public static void createFiles() {
        DataProviderXml dataProviderXml = new DataProviderXml();
        dataProviderXml.writeToXml(Art.class, new ArrayList<>(), true);
        dataProviderXml.writeToXml(Customer.class, new ArrayList<>(), true);
        dataProviderXml.writeToXml(Fursuit.class, new ArrayList<>(), true);
        dataProviderXml.writeToXml(FursuitPart.class, new ArrayList<>(), true);
        dataProviderXml.writeToXml(Material.class, new ArrayList<>(), true);
        dataProviderXml.writeToXml(Toy.class, new ArrayList<>(), true);
        dataProviderXml.writeToXml(Art.class, new ArrayList<>(), true);
    }

    private long getNextMaterialId() {
        List<Material> objectList = readFromXml(Material.class);
        long maxId = 0;
        for (Material material : objectList) {
            if (maxId < material.getId()) {
                maxId = material.getId();
            }
        }
        return maxId + 1;
    }

    private long getNextFursuitPartId(){
        List<FursuitPart> objectList = readFromXml(FursuitPart.class);
        long maxId = 0;
        for (FursuitPart fursuitPart : objectList) {
            if (maxId < fursuitPart.getId()) {
                maxId = fursuitPart.getId();
            }
        }
        return maxId + 1;
    }

    private long getNextCustomerId(){
        List<Customer> objectList = readFromXml(Customer.class);
        long maxId = 0;
        for (Customer customer : objectList) {
            if (maxId < customer.getId()) {
                maxId = customer.getId();
            }
        }
        return maxId + 1;
    }

    private long getNextProjectId(){
        List<Project> objectList = new ArrayList<>();
        objectList.addAll(readFromXml(Fursuit.class));
        objectList.addAll(readFromXml(Art.class));
        objectList.addAll(readFromXml(Toy.class));
        long maxId = 0;
        for (Project project : objectList) {
            if (maxId < project.getId()) {
                maxId = project.getId();
            }
        }
        return maxId + 1;
    }

    private <T> boolean writeToXml(Class<?> tClass, List<T> object, boolean overwrite) {
        List<T> fileObjectList;
        if (!overwrite) {
            fileObjectList = (List<T>) readFromXml(tClass);
            fileObjectList.addAll(object);
        }
        else {
            fileObjectList = new ArrayList<>(object);
        }
        try {
            File file = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.XML_FILE_EXTENSION));
            if (!file.exists()){
                file.createNewFile();
            }
            Persister serializer = new Persister();
            XmlWrapper<T> xmlWrapper = new XmlWrapper<>();
            xmlWrapper.setObjectList(fileObjectList);
            serializer.write(xmlWrapper, file);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    private <T> boolean writeToXml(T object) {
        try {
            if (object == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }
            return writeToXml(object.getClass(), Collections.singletonList(object), false);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    private <T> List<T> readFromXml(Class<T> tClass) {
        try {
            File file = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.XML_FILE_EXTENSION));
            if (!file.exists()){
                file.createNewFile();
            }
            Persister serializer = new Persister();
            XmlWrapper<T> xmlWrapper = new XmlWrapper<>();
            xmlWrapper = serializer.read(xmlWrapper.getClass(), file);
            return xmlWrapper.getObjectList();
        } catch (Exception e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    @Override
    public boolean createMaterial(long userId,
                                  String materialName,
                                  float cost,
                                  Unit unit,
                                  MaterialType materialType,
                                  String description,
                                  float inStock) {

        try {
            if (materialName == null || unit == null || materialType == null || description == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }
            Material material = new Material();
            material.setUserId(userId);
            material.setId(getNextMaterialId());
            material.setDateOfCreation(new Date(System.currentTimeMillis()));
            material.setName(materialName);
            material.setMaterialType(materialType);
            material.setCost(cost);
            material.setDescription(description);
            material.setUnit(unit);
            material.setInStock(inStock);

            return writeToXml(material);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editMaterial(long userId, Material editMaterial) {
        try {
            if (editMaterial == null ||
                    editMaterial.getMaterialType() == null
                    || editMaterial.getName() == null
                    || editMaterial.getDateOfCreation() == null
                    || editMaterial.getDescription() == null
                    || editMaterial.getUnit() == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }
            List<Material> materialList = readFromXml(Material.class);
            Optional<Material> optMaterial = materialList.stream()
                    .filter(material -> material.getId() == editMaterial.getId() && material.getUserId() == userId)
                    .findAny();
            if (optMaterial.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_MATERIAL_NOT_FOUNDED));
                return false;
            }
            materialList.remove(optMaterial.get());
            materialList.add(editMaterial);
            writeToXml(Material.class, materialList, true);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteMaterial(long userId, long id) {
        List<Material> materialList = readFromXml(Material.class);
        materialList.removeIf(material -> material.getUserId() == userId && material.getId() == id);
        writeToXml(Material.class, materialList, true);
        return true;
    }

    @Override
    public Optional<Material> getMaterial(long userId, long id)  {
        List<Material> materialList = readFromXml(Material.class);
        return materialList.stream()
                .filter(material -> material.getId() == id && material.getUserId() == userId)
                .findAny();
    }

    @Override
    public List<Material> getMaterial(long userId) {
        List<Material> materialList = readFromXml(Material.class);
        return materialList.stream()
                .filter(material -> material.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean createCustomer(long userId, String name, String url, String phoneNumber) {
        try {
            if (name == null || url == null || phoneNumber == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }

            Customer customer = new Customer();
            customer.setUserId(userId);
            customer.setId(getNextCustomerId());
            customer.setName(name);
            customer.setDateOfCreation(new Date(System.currentTimeMillis()));
            customer.setUrl(url);
            customer.setPhoneNumber(phoneNumber);

            return writeToXml(customer);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editCustomer(long userId, Customer editCustomer) {
        try {
            if (editCustomer == null ||
                    editCustomer.getName() == null ||
                    editCustomer.getDateOfCreation() == null ||
                    editCustomer.getUrl() == null ||
                    editCustomer.getPhoneNumber() == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }
            List<Customer> customerList = readFromXml(Customer.class);
            Optional<Customer> optCustomer = getCustomer(userId, editCustomer.getId());
            if (optCustomer.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_CUSTOMER_NOT_FOUNDED));
                return false;
            }
            customerList.remove(optCustomer.get());
            customerList.add(editCustomer);
            writeToXml(Customer.class, customerList, true);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(long userId, long customerId) {
        List<Customer> customerList = readFromXml(Customer.class);
        customerList.removeIf(customer -> customer.getUserId() == userId && customer.getId() == customerId);
        writeToXml(Customer.class, customerList, true);
        return true;
    }

    @Override
    public Optional<Customer> getCustomer(long userId, long customerId) {
        List<Customer> customerList = readFromXml(Customer.class);
        return customerList.stream()
                .filter(customer -> customer.getId() == customerId && customer.getUserId() == userId)
                .findAny();
    }

    @Override
    public List<Customer> getCustomer(long userId) {
        List<Customer> customerList = readFromXml(Customer.class);
        return customerList.stream()
                .filter(customer -> customer.getUserId() == userId)
                .collect(Collectors.toList());
    }


    private boolean setBasicProject(Project project,
                                    long userId,
                                    String projectName,
                                    long customerId,
                                    Date deadline,
                                    PaymentType paymentType,
                                    ProjectType projectType) {
        try {
            Optional<Customer> optionalCustomer = getCustomer(userId, customerId);
            if (optionalCustomer.isEmpty()){
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_CUSTOMER_NOT_FOUNDED));
                return false;
            }
            project.setId(getNextProjectId());
            project.setUserId(userId);
            project.setName(projectName);
            project.setDateOfCreation(new Date(System.currentTimeMillis()));
            project.setCustomer(optionalCustomer.get());
            project.setDeadline(deadline);
            project.setProjectType(projectType);
            project.setPaymentType(paymentType);
            project.setProgress(0);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean createProject(long userId,
                                 String projectName,
                                 Date deadline,
                                 long customerId,
                                 FursuitType fursuitType,
                                 FursuitStyle fursuitStyle,
                                 PaymentType paymentType) {
        try {
            Date date = new Date(System.currentTimeMillis());
            if (projectName == null
                    || deadline.before(date)
                    || fursuitType == null
                    || fursuitStyle == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }

            Fursuit project = new Fursuit();
            if (!setBasicProject(project, userId, projectName, customerId, deadline, paymentType, ProjectType.FURSUIT)) {
                return false;
            }
            project.setFursuitType(fursuitType);
            project.setFursuitStyle(fursuitStyle);
            project.setPartList(new ArrayList<>());
            return writeToXml(project);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean createProject(long userId,
                                 String projectName,
                                 Date deadline,
                                 long customerId,
                                 ArtType artType,
                                 ArtStyle artStyle,
                                 double cost,
                                 PaymentType paymentType) {
        try {
            Date date = new Date(System.currentTimeMillis());
            if (projectName == null
                    || deadline.before(date)
                    || artStyle == null
                    || artType == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }

            Art project = new Art();
            if (!setBasicProject(project, userId, projectName, customerId, deadline, paymentType, ProjectType.ART)) {
                return false;
            }
            project.setArtStyle(artStyle);
            project.setArtType(artType);
            project.setPaymentType(paymentType);
            project.setCost(cost);
            return writeToXml(project);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean createProject(long userId,
                                 String projectName,
                                 Date deadline,
                                 long customerId,
                                 ToyType toyType,
                                 ToyStyle toyStyle,
                                 PaymentType paymentType) {
        try {
            Date date = new Date(System.currentTimeMillis());
            if (projectName == null
                    || deadline.before(date)
                    || toyType == null
                    || toyStyle == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }

            Toy project = new Toy();
            if (!setBasicProject(project, userId, projectName, customerId, deadline, paymentType, ProjectType.TOY)) {
                return false;
            }
            project.setToyStyle(toyStyle);
            project.setToyType(toyType);
            project.setPaymentType(paymentType);
            project.setOutgoings(new HashMap<>());
            return writeToXml(project);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editProject(long userId, Project editedProject) {
        try {
            if (editedProject == null
                    || editedProject.getProjectType() == null
                    || editedProject.getDateOfCreation() == null
                    || editedProject.getName() == null
                    || editedProject.getDeadline() == null
                    || editedProject.getCustomer() == null
                    || editedProject.getPaymentType() == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }
            Optional<Project> optionalProject = getProject(userId, editedProject.getId());
            if (optionalProject.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
                return false;
            }
            if (!optionalProject.get().getProjectType().equals(editedProject.getProjectType())){
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
                return false;
            }

            switch (optionalProject.get().getProjectType()) {
                case FURSUIT:
                    Fursuit project = (Fursuit) optionalProject.get();
                    Fursuit editedFursuit = (Fursuit) editedProject;
                    if (!editedFursuit.getPartList().equals(project.getPartList())) {
                        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
                        return false;
                    }
                    return saveProject(Fursuit.class, editedFursuit);

                case ART:
                    return saveProject(Art.class, (Art) editedProject);
                case TOY:
                    Toy projectToy = (Toy) optionalProject.get();
                    Toy editedToy = (Toy) editedProject;
                    if (!editedToy.getOutgoings().equals(projectToy.getOutgoings())) {
                        log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
                        return false;
                    }
                    return saveProject(Toy.class, editedToy);
            }
            return false;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    private <T extends Project> boolean saveProject(Class<T> tClass, T project) {
        List<T> projectList = readFromXml(tClass);
        projectList.removeIf(tProject -> tProject.getId() == project.getId());
        projectList.add(project);
        return writeToXml(tClass, projectList, true);
    }

    @Override
    public boolean deleteProject(long userId, long projectId) {
        try {
            Optional<Project> optionalProject = getProject(userId, projectId);
            if (optionalProject.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
                return false;
            }
            switch (optionalProject.get().getProjectType()) {
                case FURSUIT:
                    Fursuit fursuit = (Fursuit) optionalProject.get();
                    fursuit.getPartList().forEach(fursuitPart -> deleteFursuitPart(userId, projectId, fursuitPart.getId()));
                    return deleteProject(Fursuit.class, fursuit);
                case ART:
                    return deleteProject(Art.class, (Art) optionalProject.get());
                case TOY:
                    return deleteProject(Toy.class, (Toy) optionalProject.get());
                }
            return false;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    private <T extends Project> boolean deleteProject(Class<T> tClass, T project) {
        List<T> projectList = readFromXml(tClass);
        projectList.removeIf(tProject -> tProject.getId() == project.getId());
        return writeToXml(tClass, projectList, true);
    }

    @Override
    public Optional<Project> getProject(long userId, long projectId) {
        List<Project> projectList = getProject(userId);
        return projectList.stream()
                .filter(project -> project.getId() == projectId)
                .findAny();
    }

    @Override
    public List<Project> getProject(long userId) {
        List<Project> projectList = new ArrayList<>();
        projectList.addAll(readFromXml(Fursuit.class));
        projectList.addAll(readFromXml(Art.class));
        projectList.addAll(readFromXml(Toy.class));
        projectList = projectList.stream()
                .filter(customer -> customer.getUserId() == userId)
                .collect(Collectors.toList());
        projectList.forEach(project -> {
            project.setCustomer(getCustomer(userId, project.getCustomer().getId()).get());
            switch (project.getProjectType()) {
                case FURSUIT:
                    Fursuit fursuit = (Fursuit) project;
                    List<FursuitPart> fursuitPartList = new ArrayList<>();
                    fursuit.getPartList().forEach(fursuitPart ->
                            fursuitPartList.add(getFursuitPart(userId, fursuitPart.getId()).get()));
                    fursuit.setPartList(fursuitPartList);
                    break;
                case TOY:
                    Toy toy = (Toy) project;
                    Map<Material, Double> materialMap = new HashMap<>();
                    toy.getOutgoings().forEach((material, aDouble) ->
                            materialMap.put(getMaterial(userId, material.getId()).get(), aDouble));
                    toy.setOutgoings(materialMap);
                    break;
            }
        });
        return projectList;
    }

    @Override
    public boolean createFursuitPart(long userId, long fursuitId, String name) {
        try {
            Optional<Project> optionalProject = getProject(userId, fursuitId);
            if (name == null || optionalProject.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }
            if (!optionalProject.get().getProjectType().equals(ProjectType.FURSUIT)) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
                return false;
            }
            Fursuit fursuit = (Fursuit) optionalProject.get();

            FursuitPart fursuitPart = new FursuitPart();
            fursuitPart.setUserId(userId);
            fursuitPart.setId(getNextFursuitPartId());
            fursuitPart.setDateOfCreation(new Date(System.currentTimeMillis()));
            fursuitPart.setName(name);
            fursuitPart.setOutgoings(new HashMap<>());

            fursuit.getPartList().add(fursuitPart);
            if (!saveProject(Fursuit.class, fursuit)){
                return false;
            }
            return writeToXml(fursuitPart);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean editFursuitPart(long userId, FursuitPart editedFursuitPart) {
        try {
            if (editedFursuitPart == null || editedFursuitPart.getName() == null) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.NULL_MSG));
                return false;
            }

            List<FursuitPart> fursuitPartsList = readFromXml(FursuitPart.class);
            Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, editedFursuitPart.getId());
            if (optFursuitPart.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FURSUIT_PART_NOT_FOUNDED));
                return false;
            }

            if (!editedFursuitPart.getOutgoings().equals(optFursuitPart.get().getOutgoings())){
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
                return false;
            }

            fursuitPartsList.remove(optFursuitPart.get());
            fursuitPartsList.add(editedFursuitPart);
            writeToXml(FursuitPart.class, fursuitPartsList, true);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    private boolean saveFursuitPart(long userId , FursuitPart editedFursuitPart){
        try {
            List<FursuitPart> fursuitPartsList = readFromXml(FursuitPart.class);
            Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, editedFursuitPart.getId());
            if (optFursuitPart.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FURSUIT_PART_NOT_FOUNDED));
                return false;
            }
            fursuitPartsList.remove(optFursuitPart.get());
            fursuitPartsList.add(editedFursuitPart);
            writeToXml(FursuitPart.class, fursuitPartsList, true);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteFursuitPart(long userId, long projectId, long partId) {
        try {
            List<FursuitPart> fursuitPartList = readFromXml(FursuitPart.class);
            Optional<Project> optionalFursuit = getProject(userId, projectId);
            if (optionalFursuit.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
                return false;
            }
            if (!optionalFursuit.get().getProjectType().equals(ProjectType.FURSUIT)) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
                return false;
            }
            Fursuit fursuit = (Fursuit) optionalFursuit.get();
            if (fursuit.getPartList().stream().noneMatch(fursuitPart -> fursuitPart.getId() == partId)) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_FORBIDDEN));
                return false;
            }
            fursuit.getPartList().removeIf(fursuitPart -> fursuitPart.getId() == partId);
            saveProject(Fursuit.class, fursuit);
            fursuitPartList.removeIf(fursuitPart -> fursuitPart.getUserId() == userId && fursuitPart.getId() == partId);
            writeToXml(FursuitPart.class, fursuitPartList, true);
            return true;
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public Optional<FursuitPart> getFursuitPart (long userId, long id) {
        List<FursuitPart> fursuitPartList = readFromXml(FursuitPart.class);
        Optional<FursuitPart> optionalFursuitPart = fursuitPartList.stream()
                .filter(fursuitPart -> fursuitPart.getId() == id && fursuitPart.getUserId() == userId)
                .findAny();
        if (optionalFursuitPart.isEmpty()) {
            return Optional.empty();
        }

        Map<Material, Double> outgoingMap = new HashMap<>();
        FursuitPart fursuitPart = optionalFursuitPart.get();

        fursuitPart.getOutgoings().forEach((material, aDouble) -> {
            Optional<Material> optMaterial = getMaterial(userId, material.getId());
            if (optMaterial.isEmpty()) {
                return;
            }
            outgoingMap.put(optMaterial.get(), aDouble);
        });
        fursuitPart.setOutgoings(outgoingMap);
        return Optional.of(fursuitPart);
    }

    @Override
    public List<FursuitPart> getFursuitPart (long userId) {
        List<FursuitPart> fursuitPartList = readFromXml(FursuitPart.class);
        fursuitPartList = fursuitPartList.stream()
                .filter(fursuitPart -> fursuitPart.getUserId() == userId)
                .collect(Collectors.toList());

        Map<Material, Double> outgoingMap = new HashMap<>();
        fursuitPartList.forEach(fursuitPart -> {
            fursuitPart.getOutgoings().forEach((material, aDouble) -> {
                Optional<Material> optMaterial = getMaterial(userId, material.getId());
                if (optMaterial.isEmpty()) {
                    return;
                }
                outgoingMap.put(optMaterial.get(), aDouble);
            });
            fursuitPart.setOutgoings(outgoingMap);
        });

        return fursuitPartList;
    }

    @Override
    public boolean setOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId, double amount) {
        try {
            Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, fursuitPartId);
            Optional<Material> optMaterial = getMaterial(userId, materialId);
            if (optFursuitPart.isEmpty() || optMaterial.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
                return false;
            }
            FursuitPart fursuitPart = optFursuitPart.get();
            Material material = optMaterial.get();
            if (fursuitPart.getOutgoings().containsKey(material)) {
                fursuitPart.getOutgoings().replace(material, fursuitPart.getOutgoings().get(material) + amount);
            }
            else {
                fursuitPart.getOutgoings().put(material, amount);
            }
            return saveFursuitPart(userId, fursuitPart);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }


    @Override
    public boolean setOutgoing(long userId, long toyId, long materialId, double amount) {
        try {
            Optional<Project> optionalToy = getProject(userId, toyId);
            Optional<Material> optMaterial = getMaterial(userId, materialId);
            if (optionalToy.isEmpty() || optMaterial.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
                return false;
            }
            if (!optionalToy.get().getProjectType().equals(ProjectType.TOY)) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
                return false;
            }
            Toy toy = (Toy) optionalToy.get();
            Material material = optMaterial.get();
            if (!toy.getOutgoings().containsKey(material)) {
                toy.getOutgoings().put(material, amount);
            } else {
                toy.getOutgoings().replace(material, amount);
            }
            return saveProject(Toy.class, toy);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }


    @Override
    public boolean deleteOutgoing(long userId, long toyId, long materialId) {
        try {
            Optional<Project> optionalToy = getProject(userId, toyId);
            Optional<Material> optMaterial = getMaterial(userId, materialId);
            if (optionalToy.isEmpty() || optMaterial.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
                return false;
            }
            if (!optionalToy.get().getProjectType().equals(ProjectType.TOY)){
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_WRONG_PROJECT_TYPE));
                return false;
            }
            Toy toy = (Toy) optionalToy.get();
            Material material = optMaterial.get();
            if (!toy.getOutgoings().containsKey(material)) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_OUTGOINGS_NOT_FOUNDED));
                return false;
            }
            toy.getOutgoings().remove(material);
            return saveProject(Toy.class, toy);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId) {
        try {
            Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, fursuitPartId);
            Optional<Material> optMaterial = getMaterial(userId, materialId);
            if (optFursuitPart.isEmpty() || optMaterial.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_EMPTY));
                return false;
            }
            FursuitPart fursuitPart = optFursuitPart.get();
            Material material = optMaterial.get();
            if (!fursuitPart.getOutgoings().containsKey(material)) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_OUTGOINGS_NOT_FOUNDED));
                return false;
            }
            fursuitPart.getOutgoings().remove(material);
            return saveFursuitPart(userId, fursuitPart);
        } catch (IOException e) {
            log.error(e);
            return false;
        }
    }

    private String customerToString(Customer customer) throws IOException {
        return String.format(ConfigurationUtil.getConfigurationEntry(Constants.CUSTOMER_TO_STRING),
                customer.getName(),
                customer.getUrl(),
                customer.getPhoneNumber());
    }

    private String projectToString(Project project) throws IOException {
        String stringProject = String.format(ConfigurationUtil.getConfigurationEntry(Constants.PROJECT_BASE_TO_STRING),
                project.getProjectType().toString(),
                project.getName(),
                customerToString(project.getCustomer()),
                project.getPaymentType().toString(),
                project.getDateOfCreation().toString(),
                project.getDeadline().toString(),
                project.getProgress() * 100);
        switch (project.getProjectType()) {
            case ART:
                Art artProject = (Art) project;
                stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.ART_PROJECT_TO_STRING),
                        artProject.getArtType().toString(),
                        artProject.getArtStyle().toString(),
                        artProject.getCost());
                return stringProject;
            case TOY:
                Toy toyProject = (Toy) project;
                stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.TOY_PROJECT_TO_STRING),
                        toyProject.getToyType().toString(),
                        toyProject.getToyStyle().toString());
                break;
            case FURSUIT:
                Fursuit fursuitProject = (Fursuit) project;
                stringProject += String.format(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PROJECT_TO_STRING),
                        fursuitProject.getFursuitType().toString(),
                        fursuitProject.getFursuitStyle().toString());
                break;
        }
        return stringProject;
    }

    private String fursuitPartToString(FursuitPart fursuitPart) throws IOException {
        return String.format(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PART_TO_STRING),
                fursuitPart.getName(),
                fursuitPart.getProgress() * 100,
                fursuitPart.getDateOfCreation().toString());
    }

    private String outgoingsWithCostsToString(Map<Material, Double> outgoingMap, Map<Material, Double> costsMap) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.OUTGOING_TITLES));
        outgoingMap.forEach((material, aDouble) ->
        {
            try {
                stringBuilder.append(String.format(ConfigurationUtil.getConfigurationEntry(Constants.OUTGOINGS_TO_STRING),
                        material.getName(),
                        material.getCost(),
                        aDouble,
                        costsMap.get(material)));
            } catch (IOException e) {
                log.error(e);
            }
        });
        return stringBuilder.toString();
    }

    private Map<Material, Double> calculateCosts(Map<Material, Double> outgoingMap) {
        Map<Material, Double> calculatedCosts = new HashMap<>();
        outgoingMap.forEach((material, aDouble) ->
                calculatedCosts.put(material, material.getCost() * aDouble));
        return calculatedCosts;
    }

    @Override
    public String getProjectEstimate(long userId) {
        List<Project> projectList = getProject(userId);
        StringBuilder stringBuilder = new StringBuilder();
        projectList.forEach(project -> stringBuilder.append(getProjectEstimate(userId, project.getId())));
        return stringBuilder.toString();
    }

    @Override
    public String getProjectEstimate(long userId, long projectId) {
        try {
            Optional<Project> project = getProject(userId, projectId);
            if (project.isEmpty()) {
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
                return "";
            }
            switch (project.get().getProjectType()) {
                case ART:
                    return projectToString(project.get());
                case TOY:
                    Toy toyProject = (Toy) project.get();
                    Map<Material, Double> costsMapToy = calculateCosts(toyProject.getOutgoings());
                    return projectToString(toyProject) +
                            ConfigurationUtil.getConfigurationEntry(Constants.ESTIMATE_TITLE) +
                            outgoingsWithCostsToString(toyProject.getOutgoings(), costsMapToy) +
                            ConfigurationUtil.getConfigurationEntry(Constants.AMOUNT_TITLE) +
                            calculateProjectCost(userId, projectId) +
                            ConfigurationUtil.getConfigurationEntry(Constants.NEW_LINE);
                case FURSUIT:
                    Fursuit fursuitProject = (Fursuit) project.get();
                    Map<FursuitPart, Map<Material, Double>> outgoingMapList = new HashMap<>();
                    fursuitProject.getPartList().forEach(fursuitPart ->
                            outgoingMapList.put(fursuitPart, calculateCosts(fursuitPart.getOutgoings())));
                    StringBuilder estimateBuilder = new StringBuilder();
                    estimateBuilder.append(projectToString(fursuitProject));
                    estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.FURSUIT_PARTS_TITLE));
                    outgoingMapList.forEach((fursuitPart, costsMap) ->
                    {
                        try {
                            estimateBuilder.append(fursuitPartToString(fursuitPart));
                            estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.ESTIMATE_TITLE));
                            estimateBuilder.append(outgoingsWithCostsToString(fursuitPart.getOutgoings(), costsMap));
                        } catch (IOException e) {
                            log.error(e);
                        }
                    });
                    estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.AMOUNT_TITLE));
                    estimateBuilder.append(calculateProjectCost(userId, projectId));
                    estimateBuilder.append(ConfigurationUtil.getConfigurationEntry(Constants.NEW_LINE));
                    return estimateBuilder.toString();
            }
            return  "";
        } catch (IOException e) {
            log.error(e);
            return "";
        }
    }

    @Override
    public double calculateProjectCost (long userId, long projectId) {
        try {
            Optional<Project> optionalProject = getProject(userId, projectId);
            if (optionalProject.isEmpty()){
                log.error(ConfigurationUtil.getConfigurationEntry(Constants.MSG_PROJECT_NOT_FOUNDED));
                return 0;
            }
            switch (optionalProject.get().getProjectType()){
                case ART:
                    return ((Art)optionalProject.get()).getCost();
                case TOY:
                    return calculateCosts(((Toy)optionalProject.get()).getOutgoings()).values().stream()
                            .mapToDouble(value -> value)
                            .sum();
                case FURSUIT:
                    return ((Fursuit)optionalProject.get())
                            .getPartList()
                            .stream()
                            .mapToDouble(fursuitPart ->
                                    calculateCosts(fursuitPart.getOutgoings())
                                            .values()
                                            .stream()
                                            .mapToDouble(value -> value)
                                            .sum())
                            .sum();
                }
            return 0;
        } catch (IOException e) {
            log.error(e);
            return 0;
        }
    }
}
