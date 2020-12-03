package ru.sfedu.studyProject.DataProviders;


import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

public interface DataProvider {
    boolean addMaterial (long userId,
                         String materialName,
                         float cost,
                         Unit unit,
                         MaterialType materialType,
                         String description,
                         float inStock) throws IOException;
    boolean editMaterial (long userId, Material editedMaterial);
    boolean deleteMaterial (long userId, Material Material);

    Optional<Material> getMaterial (long userId, Long materialId);
    List<Material> getMaterial (long userId);

    boolean createProject (long userId,
                           String projectName,
                           Date deadline,
                           long customerId,
                           ProjectType type,
                           FursuitType fursuitType,
                           FursuitStyle fursuitStyle);
    boolean createProject (long userId,
                           String projectName,
                           Date deadline,
                           long customerId,
                           ProjectType type,
                           ArtType artType,
                           ArtStyle artStyle);
    boolean createProject (long userId,
                           String projectName,
                           Date deadline,
                           long customerId,
                           ProjectType type,
                           ToyType toyType,
                           ToyStyle toyStyle);

    boolean editProject (long userId, Project editedProject);
    boolean deleteProject (long userId, Project project);
    boolean createFursuitPart (long userId, Project project, FursuitType type);
    boolean editFursuitPart (long userId, Project project, FursuitPart editedFursuitPart);
    boolean deleteFursuitPart (long userId, Project project, long partId);

    boolean addOutgoing (long userId, Art artProject, Material material, Double amount);
    boolean addOutgoing (long userId, Toy toyProject, Material material, Double amount);
    boolean addOutgoing (long userId, FursuitPart fursuitPart, Material material, Double amount);
    boolean editOutgoing (long userId, Project project, Material material, Double amount);
    boolean deleteOutgoing (long userId, Project project, Material outgoing);

    String getProjectEstimate (long userId);
    String getProjectEstimate (long userId, long projectId);

    Optional <Project> getProject (long userId, long projectId);
    List <Project> getProject (long userId);
    String getPreviewPrice (long userId, Map <Material, Double> outgoingMap);

    Double calculateProjectCost (Map <Material, Double> outgoingMap);
}
