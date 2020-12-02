package ru.sfedu.studyProject.DataProviders;


import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

public interface DataProvider {
    boolean addMaterial (long userID, String materialName, Double cost, Unit units);
    boolean editMaterial (long userId, Material editedMaterial);
    boolean deleteMaterial (long userId, Material Material);

    Optional<Material> getMaterial (long userId, Long materialId);
    //TODO Удалить
    Optional<Material> getMaterial (long userId);
    //TODO Удалить
    List<Material> getMaterialList (long userId, Long materialId);
    //TODO переименовать в getMaterial
    List<Material> getMaterialList (long userId);

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
    //TODO удалить
    boolean closeProject (long userId, Project project);
    //TODO переименовать в createFursuitPart
    boolean createPartToFursuitProject (long userId, Project project, FursuitType type);
    boolean editFursuitPart (long userId, Project project, FursuitPart editedFursuitPart);
    boolean deleteFursuitPart (long userId, Project project, long partId);
    //TODO удалить
    boolean addOutgoing (long userId, Art artProject, Map<Material, Double> outgoingMap);
    boolean addOutgoing (long userId, Art artProject, Material material, Double amount);
    //TODO удалить
    boolean addOutgoing (long userId, Toy toyProject, Map <Material, Double>  outgoingMap);
    boolean addOutgoing (long userId, Toy toyProject, Material material, Double amount);
    //TODO удалить
    boolean addOutgoing (long userId, FursuitPart fursuitPart, Map <Material, Double>  outgoingMap);
    boolean addOutgoing (long userId, FursuitPart fursuitPart, Material material, Double amount);
    //TODO изменить  Map <Material, Double> editedOutgoingMap на Material material, double amount
    boolean editOutgoing (long userId, Project project, Map <Material, Double> editedOutgoingMap);
    //TODO опечатка
    boolean deleteOutgoung (long userId, Project project, Material outgoing);

    String getProjectEstimate (long userId);
    String getProjectEstimate (long userId, long projectId);

    Optional <Project> getProject (long userId, long projectId);
    List <Project> getProject (long userId);
    //TODO тут название не как в диаграмме классов
    String previewPrice (long userId, Map <Material, Double> outgoingMap);

    Double calculateProjectCost (Map <Material, Double> outgoingMap);
}
