package ru.sfedu.studyProject.DataProviders;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;
import ru.sfedu.studyProject.utils.ConfigurationUtil;

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

    @Override
    public boolean addMaterial(long userId,
                               String materialName,
                               float cost,
                               Unit unit,
                               MaterialType materialType,
                               String description,
                               float inStock) throws IOException {
        Material material = new Material();
        material.setUserId(userId);
        material.setId(0);
        material.setDateOfCreation(new Date(System.currentTimeMillis()));
        material.setName(materialName);
        material.setMaterialType(materialType);
        material.setCost(cost);
        material.setDescription(description);
        material.setUnit(unit);
        material.setInStock(inStock);

        if (materialName == null || unit == null) {
            return false;
        };

        FileWriter writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(path)
                + materialName.getClass().getSimpleName().toLowerCase()
                + ConfigurationUtil.getConfigurationEntry(file_extension));
        CSVWriter csvWriter = new CSVWriter(writer);
        StatefulBeanToCsv<Material> beanToCsv = new StatefulBeanToCsvBuilder<Material>(csvWriter)
                .withApplyQuotesToAll(false)
                .build();
        try {
            beanToCsv.write(material);
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
        csvWriter.close();
        return true;
    }

    @Override
    public boolean editMaterial(long userId, Material editedMaterial) {
        if (editedMaterial == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteMaterial(long userId, Material Material) {
        if (Material == null) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Material> getMaterial(long userId, Long materialId) {
        return Optional.empty();
    }

    @Override
    public List<Material> getMaterial(long userId) {
        return null;
    }

    @Override
    public boolean createProject(long userId, String projectName, Date deadline, long customerId, ProjectType type, FursuitType fursuitType, FursuitStyle fursuitStyle) {
        Date date = new Date();
        if (projectName == null || deadline.before(date) || type == null || fursuitType == null || fursuitStyle == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createProject(long userId, String projectName, Date deadline, long customerId, ProjectType type, ArtType artType, ArtStyle artStyle) {
        Date date = new Date();
        if (projectName == null || deadline.before(date) || type == null || artType == null || artStyle == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createProject(long userId, String projectName, Date deadline, long customerId, ProjectType type, ToyType toyType, ToyStyle toyStyle) {
        Date date = new Date();
        if (projectName == null || deadline.before(date) || type == null || toyType == null || toyStyle == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editProject(long userId, Project editedProject) {
        if (editedProject == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteProject(long userId, Project project) {
        if (project == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createFursuitPart(long userId, Project project, FursuitType type) {
        if (project == null || type == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editFursuitPart(long userId, Project project, FursuitPart editedFursuitPart) {
        if (project == null || editedFursuitPart == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFursuitPart(long userId, Project project, long partId) {
        if (project == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addOutgoing(long userId, Art artProject, Material material, Double amount) {
        if (artProject == null || material == null || amount == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addOutgoing(long userId, Toy toyProject, Material material, Double amount) {
        if (toyProject == null || material == null || amount == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addOutgoing(long userId, FursuitPart fursuitPart, Material material, Double amount) {
        if (fursuitPart == null || material == null || amount == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editOutgoing(long userId, Project project, Material material, Double amount) {
        if (project == null || material == null || amount == null) {
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
    public String getProjectEstimate(long userId) {
        return null;
    }

    @Override
    public String getProjectEstimate(long userId, long projectId) {
        return null;
    }

    @Override
    public Optional<Project> getProject(long userId, long projectId) {
        return Optional.empty();
    }

    @Override
    public List<Project> getProject(long userId) {
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
