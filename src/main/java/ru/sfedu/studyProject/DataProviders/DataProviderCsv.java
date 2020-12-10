package ru.sfedu.studyProject.DataProviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataProviderCsv implements DataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCsv.class);
    private final String path = "csv_path";
    private final String file_extension = "csv_extension";

    private <T> boolean writeToCsv (Class<?> tClass, List<T> object) {
        CSVWriter csvWriter;
        try {
            FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(path)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(file_extension));
            csvWriter = new CSVWriter(writer);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(object);
            csvWriter.close();
            return true;
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e ) {
            log.error(e);
            return false;
        } finally {
            csvWriter.close();
        }
    }

    private <T> boolean writeToCsv (T object) {
        if (object == null) {
            log.error("something is null");
            return false;
        }
        return writeToCsv(object.getClass(), Collections.singletonList(object));
    }

    private <T> List<T> readFromCsv (Class<T> tClass) {
        try {
            FileReader reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH)
                    + tClass.getSimpleName().toLowerCase()
                    + ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION));

            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                    .withType(tClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
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

        if (materialName == null || unit == null || materialType == null || description == null) {
            log.error("something is null");
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

        return writeToCsv(material);
    }

    @Override
    public boolean editMaterial(long userId, Material editMaterial) {
        if (editMaterial == null ||
                editMaterial.getMaterialType() == null
                || editMaterial.getName() == null
                || editMaterial.getDateOfCreation() == null
                || editMaterial.getDescription() == null
                || editMaterial.getUnit() == null) {
            log.error("something is null");
            return false;
        }
        List<Material> materialList = readFromCsv(Material.class);
        Optional<Material> optMaterial = materialList.stream()
                .filter(material -> material.getId() == editMaterial.getId() && material.getUserId() == userId)
                .findAny();
        if (optMaterial.isEmpty()) {
            log.error("material not founded");
            return false;
        }
        materialList.remove(optMaterial.get());
        materialList.add(editMaterial);
        writeToCsv(Material.class, materialList, true);
        return true;
    }

    @Override
    public boolean deleteMaterial(long userId, long id) {
        List<Material> materialList = readFromCsv(Material.class);
        materialList.removeIf(material -> material.getUserId() == userId && material.getId() == id);
        writeToCsv(Material.class, materialList, true);
        return true;
    }

    @Override
    public Optional<Material> getMaterial(long userId, long id)  {
        List<Material> materialList = readFromCsv(Material.class);
        return materialList.stream()
                .filter(material -> material.getId() == id && material.getUserId() == userId)
                .findAny();
    }

    @Override
    public List<Material> getMaterial(long userId) {
        List<Material> materialList = readFromCsv(Material.class);
        return materialList.stream()
                .filter(material -> material.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean createCustomer(long userId, String name, String url, String phoneNumber) {
        if (name == null || url == null || phoneNumber == null) {
            log.error("something is null");
            return false;
        }

        Customer customer = new Customer();
        customer.setUserId(userId);
        customer.setId(getNextCustomerId());
        customer.setName(name);
        customer.setDateOfCreation(new Date(System.currentTimeMillis()));
        customer.setUrl(url);
        customer.setPhoneNumber(phoneNumber);

        return writeToCsv(customer);
    }

    @Override
    public boolean editCustomer(long userId, Customer editCustomer) {
        if (editCustomer == null ||
            editCustomer.getName() == null ||
            editCustomer.getDateOfCreation() == null ||
            editCustomer.getUrl() == null ||
            editCustomer.getPhoneNumber() == null) {
            log.error("something is null");
            return false;
        }
        List<Customer> customerList = readFromCsv(Customer.class);
        Optional<Customer> optCustomer = getCustomer(userId, editCustomer.getId());
        if (optCustomer.isEmpty()) {
            log.error("Customer not founded");
            return false;
        }
        customerList.remove(optCustomer.get());
        customerList.add(editCustomer);
        writeToCsv(Customer.class, customerList, true);
        return true;
    }

    @Override
    public boolean deleteCustomer(long userId, long customerId) {
        List<Customer> customerList = readFromCsv(Customer.class);
        customerList.removeIf(customer -> customer.getUserId() == userId && customer.getId() == customerId);
        writeToCsv(Customer.class, customerList, true);
        return true;
    }

    @Override
    public Optional<Customer> getCustomer(long userId, long customerId) {
        List<Customer> customerList = readFromCsv(Customer.class);
        return customerList.stream()
                .filter(customer -> customer.getId() == customerId && customer.getUserId() == userId)
                .findAny();
    }

    @Override
    public List<Customer> getCustomer(long userId) {
        List<Customer> customerList = readFromCsv(Customer.class);
        return customerList.stream()
                .filter(customer -> customer.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean createProject(long userId,
                                 String projectName,
                                 Date deadline,
                                 long customerId,
                                 FursuitType fursuitType,
                                 FursuitStyle fursuitStyle,
                                 PaymentType paymentType) {

        Date date = new Date(System.currentTimeMillis());
        if (projectName == null
                || deadline.before(date)
                || fursuitType == null
                || fursuitStyle == null) {
            log.error("Something is null");
            return false;
        }

        Fursuit project = new Fursuit();
        if (!setBasicProject(project, userId, projectName, customerId, deadline, paymentType, ProjectType.FURSUIT)) {
            return false;
        }
        project.setFursuitType(fursuitType);
        project.setFursuitStyle(fursuitStyle);
        project.setPartList(new ArrayList<>());
    return writeToCsv(project);
    }

    @Override
    public boolean createProject(long userId,
                                 String projectName,
                                 Date deadline,
                                 long customerId,
                                 ArtType artType,
                                 ArtStyle artStyle,
                                 PaymentType paymentType) {
        Date date = new Date(System.currentTimeMillis());
        if (projectName == null
                || deadline.before(date)
                || artStyle == null
                || artType == null) {
            log.error("Something is null");
            return false;
        }

        Art project = new Art();
        if (!setBasicProject(project, userId, projectName, customerId, deadline, paymentType, ProjectType.ART)) {
            return false;
        }
        project.setArtStyle(artStyle);
        project.setArtType(artType);
        project.setPaymentType(paymentType);
        return writeToCsv(project);
    }

    @Override
    public boolean createProject(long userId,
                                 String projectName,
                                 Date deadline,
                                 long customerId,
                                 ToyType toyType,
                                 ToyStyle toyStyle,
                                 PaymentType paymentType) {

        Date date = new Date(System.currentTimeMillis());
        if (projectName == null
                || deadline.before(date)
                || toyType == null
                || toyStyle == null) {
            log.error("Something is null");
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
        return writeToCsv(project);
    }

    @Override
    public boolean editProject(long userId, Project editedProject) {
        if (editedProject == null
            || editedProject.getProjectType() == null
            || editedProject.getDateOfCreation() == null
            || editedProject.getName() == null
            || editedProject.getDeadline() == null
            || editedProject.getCustomer() == null
            || editedProject.getPaymentType() == null) {
            log.error("something is null");
            return false;
        }
        Optional<Project> optionalProject = getProject(userId, editedProject.getId());
        if (optionalProject.isEmpty()) {
            log.error("project not founded");
            return false;
        }
        if (!optionalProject.get().getProjectType().equals(editedProject.getProjectType())){
            log.error("wrong project type");
            return false;
        }

        switch (optionalProject.get().getProjectType()) {
            case FURSUIT -> {
                Fursuit project = (Fursuit) optionalProject.get();
                Fursuit editedFursuit = (Fursuit) editedProject;
                if (!editedFursuit.getPartList().equals(project.getPartList())) {
                    log.error("Forbidden");
                    return false;
                }
                return saveProject(Fursuit.class, editedFursuit);
            }

            case ART -> {
                return saveProject(Art.class,(Art) editedProject);
            }

            case TOY -> {
                Toy project = (Toy) optionalProject.get();
                Toy editedToy = (Toy) editedProject;
                if (!editedToy.getOutgoings().equals(project.getOutgoings())) {
                    log.error("Forbidden");
                    return false;
                }
                return saveProject(Toy.class, editedToy);
            }
        }
        return false;
    }

    @Override
    public boolean deleteProject(long userId, Project project) {
        if (project == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createFursuitPart(long userId, String name/*, Map<Material, Long> outgoings*/) {
        if (name == null) {
            log.error("something is null");
            return false;
        }
        if (!optionalProject.get().getProjectType().equals(ProjectType.FURSUIT)) {
            log.error("Wrong project type");
            return false;
        }
        Fursuit fursuit = (Fursuit) optionalProject.get();

        FursuitPart fursuitPart = new FursuitPart();
        fursuitPart.setUserId(userId);
        fursuitPart.setId(getNextFursuitPartId());
        fursuitPart.setDateOfCreation(new Date(System.currentTimeMillis()));
        fursuitPart.setName(name);

        return writeToCsv(fursuitPart);
    }

    @Override
    public boolean editFursuitPart(long userId, FursuitPart editedFursuitPart) {
        if (editedFursuitPart == null || editedFursuitPart.getName() == null) {
            log.error("something is null");
            return false;
        }
        if (editedFursuitPart.getName() == null) {
            log.error("something is null");
            return false;
        }
        List<FursuitPart> fursuitPartsList = readFromCsv(FursuitPart.class);
        Optional<FursuitPart> optFursuitPart = fursuitPartsList.stream()
                .filter(fursuitParts -> fursuitParts.getId() == editedFursuitPart.getId()
                        && fursuitParts.getUserId() == userId)
                .findAny();
        if (optFursuitPart.isEmpty()) {
            log.error("Fursuit part not founded");
            return false;
        }
        fursuitPartsList.remove(optFursuitPart.get());
        fursuitPartsList.add(editedFursuitPart);
        writeToCsv(FursuitPart.class, fursuitPartsList, true);
        return true;
    }

    @Override
    public boolean deleteFursuitPart(long userId, long partId) {
        List<FursuitPart> fursuitPartList = readFromCsv(FursuitPart.class);
        fursuitPartList.removeIf(fursuitPart -> fursuitPart.getUserId() == userId && fursuitPart.getId() == partId);
        writeToCsv(FursuitPart.class, fursuitPartList, true);
        return true;
    }

    @Override
    public Optional<FursuitPart> getFursuitPart (long userId, long id) {
        List<FursuitPart> fursuitPartList = readFromCsv(FursuitPart.class);
        return fursuitPartList.stream()
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
        List<FursuitPart> fursuitPartList = readFromCsv(FursuitPart.class);
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
    public boolean addOutgoing(long userId, long projectId, long materialId, double amount) {

        return true;
    }

    @Override
    public boolean addOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId, double amount) {
        Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, fursuitPartId);
        Optional<Material> optMaterial = getMaterial(userId, materialId);
        if (optFursuitPart.isEmpty() || optMaterial.isEmpty()) {
            log.error("Something is empty");
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
    }

    @Override
    public boolean editOutgoing(long userId, Project project, Material material, double amount) {
        if (project == null || material == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOutgoing(long userId, Project project, Material outgoing) {
        if (project == null || outgoing == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOutgoing(long userId, long toyId, long materialId) {
        Optional<Project> optionalToy = getProject(userId, toyId);
        Optional<Material> optMaterial = getMaterial(userId, materialId);
        if (optionalToy.isEmpty() || optMaterial.isEmpty()) {
            log.error("Something not founded");
            return false;
        }
        if (!optionalToy.get().getProjectType().equals(ProjectType.TOY)){
            log.error("Wrong project type");
            return false;
        }
        Toy toy = (Toy) optionalToy.get();
        Material material = optMaterial.get();
        if (!toy.getOutgoings().containsKey(material)) {
            log.error("Outgoing not founded");
            return false;
        }
        toy.getOutgoings().remove(material);
        return saveProject(Toy.class, toy);
    }

    @Override
    public boolean deleteOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId) {
        Optional<FursuitPart> optFursuitPart = getFursuitPart(userId, fursuitPartId);
        Optional<Material> optMaterial = getMaterial(userId, materialId);
        if (optFursuitPart.isEmpty() || optMaterial.isEmpty()) {
            log.error("Something is empty");
            return false;
        }
        FursuitPart fursuitPart = optFursuitPart.get();
        Material material = optMaterial.get();
        if (!fursuitPart.getOutgoings().containsKey(material)) {
            log.error("Outgoing not founded");
            return false;
        }
        fursuitPart.getOutgoings().remove(material);
        return saveFursuitPart(userId, fursuitPart);
    }

    @Override
    public String getProjectEstimate(long userId) {
        return null;
    }

    @Override
    public String getProjectEstimate(long userId, long projectId) {
        return null;
    }

    @Override
    public String getPreviewPrice(long userId, Map<Material, Double> outgoingMap) {
        return null;
    }

    @Override
    public Double calculateProjectCost(Map<Material, Double> outgoingMap) {
        return null;
    }
}
