package ru.sfedu.studyProject.DataProviders;


import ru.sfedu.studyProject.enums.*;
import ru.sfedu.studyProject.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

/**
 * The interface Data provider.
 */
public interface DataProvider {
    /**
     * Adding information about the material.
     *
     * @param userId       the user id
     * @param materialName the material name
     * @param cost         the cost
     * @param unit         the unit
     * @param materialType the material type
     * @param description  the description
     * @param inStock      the in stock
     * @return the boolean
     */
    boolean createMaterial (long userId,
                         String materialName,
                         float cost,
                         Unit unit,
                         MaterialType materialType,
                         String description,
                         float inStock) throws IOException;
    boolean editMaterial (long userId, Material editedMaterial);
    boolean deleteMaterial (long userId, long id);

    /**
     * Getting information about a specific material from the list.
     *
     * @param userId     the user id
     * @param materialId the material id
     * @return the material
     */
    Optional<Material> getMaterial (long userId, long materialId);

    /**
     * Getting information about all materials.
     *
     * @param userId the user id
     * @return the material
     */
    List<Material> getMaterial (long userId);

    /**
     * Creating a project with the Fursuit type for projects related to creating fursuits.
     *
     * @param userId       the user id
     * @param projectName  the project name
     * @param deadline     the deadline
     * @param customerId   the customer id
     * @param fursuitType  the fursuit type
     * @param fursuitStyle the fursuit style
     * @param paymentType  the payment type
     * @return the boolean
     */
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
                           double cost,
                           PaymentType paymentType);

    /**
     * Creating a project with the Toy type for projects related to creating various toys.
     *
     * @param userId      the user id
     * @param projectName the project name
     * @param deadline    the deadline
     * @param customerId  the customer id
     * @param toyType     the toy type
     * @param toyStyle    the toy style
     * @param paymentType the payment type
     * @return the boolean
     */
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

    /**
     * Creating a part in a Fursuit project.
     *
     * @param userId    the user id
     * @param fursuitId the fursuit id
     * @param name      the name
     * @return the boolean
     */
    boolean createFursuitPart (long userId, long fursuitId, String name);

    /**
     * Changing part data from a Fursuit project.
     *
     * @param userId            the user id
     * @param editedFursuitPart the edited fursuit part
     * @return the boolean
     */
    boolean editFursuitPart (long userId, FursuitPart editedFursuitPart);

    /**
     * Deleting a part from a Fursuit project.
     *
     * @param userId    the user id
     * @param projectId the project id
     * @param partId    the part id
     * @return the boolean
     */
    boolean deleteFursuitPart (long userId, long projectId, long partId);

    /**
     * Getting information about one part of a fursuit.
     *
     * @param userId the user id
     * @param id     the id
     * @return the fursuit part
     */
    Optional<FursuitPart> getFursuitPart (long userId, long id);

    /**
     * Getting information about all parts of the fursuit.
     *
     * @param userId the user id
     * @return the fursuit part
     */
    List<FursuitPart> getFursuitPart (long userId);

    /**
     * Setting up expenses for a Toy project.
     *
     * @param userId     the user id
     * @param toyId      the toy id
     * @param materialId the material id
     * @param amount     the amount
     * @return the outgoing
     */
    boolean setOutgoing (long userId, long toyId, long materialId, double amount);

    /**
     * Setting up expenses in the Fursuit part of the project.
     *
     * @param userId        the user id
     * @param projectId     the project id
     * @param fursuitPartId the fursuit part id
     * @param materialId    the material id
     * @param amount        the amount
     * @return the outgoing
     */
    boolean setOutgoing (long userId, long projectId, long fursuitPartId, long materialId, double amount);

    /**
     * Deleting a material expense from a Toy project.
     *
     * @param userId     the user id
     * @param toyId      the toy id
     * @param materialId the material id
     * @return the boolean
     */
    boolean deleteOutgoing(long userId, long toyId, long materialId);

    /**
     * Deleting a material expense from a Fursuit project.
     *
     * @param userId        the user id
     * @param fursuitId     the fursuit id
     * @param fursuitPartId the fursuit part id
     * @param materialId    the material id
     * @return the boolean
     */
    boolean deleteOutgoing(long userId, long fursuitId, long fursuitPartId, long materialId);

    /**
     * Getting estimates for all projects that the user has.
     *
     * @param userId the user id
     * @return the project estimate
     */
    String getProjectEstimate(long userId);

    /**
     * Getting estimates for a single user project.
     *
     * @param userId    the user id
     * @param projectId the project id
     * @return the project estimate
     */
    String getProjectEstimate (long userId, long projectId);

    /**
     * Getting information about one project by its id.
     *
     * @param userId    the user id
     * @param projectId the project id
     * @return the project
     */
    Optional <Project> getProject (long userId, long projectId);
    List <Project> getProject (long userId);
    String getPreviewPrice (long userId, Map <Material, Double> outgoingMap);

    /**
     * Calculation of project costs by adding the cost of materials and work.
     *
     * @param userId    the user id
     * @param projectId the project id
     * @return the double
     */
    double calculateProjectCost (long userId, long projectId);

    /**
     * Adding information about the customer
     *
     * @param userId       the user id
     * @param customerName the customer name
     * @param url          the url
     * @param phoneNumber  the phone number
     * @return the boolean
     */
    boolean createCustomer (long userId, String customerName, String url, String phoneNumber);

    /**
     * Changing any information about the customer.
     *
     * @param userId       the user id
     * @param editCustomer the edit customer
     * @return the boolean
     */
    boolean editCustomer (long userId, Customer editCustomer);

    /**
     * Deleting all data of a single customer from the list.
     *
     * @param userId     the user id
     * @param customerId the customer id
     * @return the boolean
     */
    boolean deleteCustomer (long userId, long customerId);

    /**
     * Getting information about a specific customer from the list.
     *
     * @param userId     the user id
     * @param customerId the customer id
     * @return the customer
     */
    Optional <Customer> getCustomer (long userId, long customerId);

    /**
     * Getting information about all customers.
     *
     * @param userId the user id
     * @return the customer
     */
    List <Customer> getCustomer (long userId);
}
