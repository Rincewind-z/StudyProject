package ru.sfedu.studyProject.DataProviders;


import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

public interface DataProvider {
    boolean createMaterial (long userId,
                         String materialName,
                         float cost,
                         Unit unit,
                         MaterialType materialType,
                         String description,
                         float inStock) throws IOException;
    boolean editMaterial (long userId, Material editedMaterial);
    boolean deleteMaterial (long userId, long id);

    Optional<Material> getMaterial (long userId, long materialId);
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
                           ArtStyle artStyle,
                           PaymentType paymentType);
    boolean createProject (long userId,
                           String projectName,
                           Date deadline,
                           long customerId,
                           ProjectType type,
                           ToyType toyType,
                           ToyStyle toyStyle,
                           PaymentType paymentType);

    boolean editProject (long userId, Project editedProject);
    boolean deleteProject (long userId, Project project);

    boolean createFursuitPart (long userId, long fursuitId, String name);
    boolean editFursuitPart (long userId, FursuitPart editedFursuitPart);
    boolean deleteFursuitPart (long userId, long projectId, long partId);

    Optional<FursuitPart> getFursuitPart (long userId, long id);
    List<FursuitPart> getFursuitPart (long userId);
    boolean setOutgoing (long userId, long toyId, long materialId, double amount);
    boolean setOutgoing (long userId, long projectId, long fursuitPartId, long materialId, double amount);
    boolean deleteOutgoing(long userId, long toyId, long materialId);
    boolean deleteOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId);
    String getProjectEstimate(long userId);
    String getProjectEstimate (long userId, long projectId);

    Optional <Project> getProject (long userId, long projectId);
    List <Project> getProject (long userId);
    String getPreviewPrice (long userId, Map <Material, Double> outgoingMap);

    Double calculateProjectCost (Map <Material, Double> outgoingMap);

    boolean createCustomer (long userId, String customerName, String url, String phoneNumber);
    boolean editCustomer (long userId, Customer editCustomer);
    boolean deleteCustomer (long userId, long customerId);

    Optional <Customer> getCustomer (long userId, long customerId);
    List <Customer> getCustomer (long userId);
}
