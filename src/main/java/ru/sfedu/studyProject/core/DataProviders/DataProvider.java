package ru.sfedu.studyProject.core.DataProviders;

import ru.sfedu.studyProject.core.enums.*;
import ru.sfedu.studyProject.core.model.Customer;
import ru.sfedu.studyProject.core.model.FursuitPart;
import ru.sfedu.studyProject.core.model.Material;
import ru.sfedu.studyProject.core.model.Project;

import java.util.Date;
import java.util.List;

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
                         float inStock);

    /**
     * Changing any material information.
     *
     * @param userId       the user id
     * @param editMaterial the edit material
     * @return the boolean
     */
    boolean editMaterial (long userId, Material editMaterial);

    /**
     * Deleting a specific material from the list.
     *
     * @param userId     the user id
     * @param materialId the material id
     * @return the boolean
     */
    boolean deleteMaterial (long userId, long materialId);

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
                           Date deadline, long customerId,
                           FursuitType fursuitType,
                           FursuitStyle fursuitStyle,
                           PaymentType paymentType);

    /**
     * Creating an Art project for drawing-related projects.
     *
     * @param userId      the user id
     * @param projectName the project name
     * @param deadline    the deadline
     * @param customerId  the customer id
     * @param artType     the art type
     * @param artStyle    the art style
     * @param cost        the cost
     * @param paymentType the payment type
     * @return the boolean
     */
    boolean createProject (long userId,
                           String projectName,
                           Date deadline,
                           long customerId,
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
                           ToyType toyType,
                           ToyStyle toyStyle,
                           PaymentType paymentType);

    /**
     * Edit information about the project.
     *
     * @param userId        the user id
     * @param editedProject the edited project
     * @return the boolean
     */
    boolean editProject (long userId, Project editedProject);

    /**
     * Deleting a project.
     *
     * @param userId    the user id
     * @param projectId the project id
     * @return the boolean
     */
    boolean deleteProject (long userId,  long projectId);

    /**
     * Creating a part in a Fursuit project.
     *
     * @param userId    the user id
     * @param fursuitId the fursuit id
     * @param name      the name
     * @param cost      the cost
     * @return the boolean
     */
    boolean createFursuitPart (long userId, long fursuitId, String name, double cost);

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
     * Deleting a material expense from a Toy project.
     *
     * @param userId     the user id
     * @param toyId      the toy id
     * @param materialId the material id
     * @return the boolean
     */
    boolean deleteOutgoing(long userId, long toyId, long materialId);

    /**
     * Getting estimates for all projects that the user has.
     *
     * @param userId the user id
     * @return the project estimate
     */
    String getProjectEstimate(long userId);

    /**
     * Getting information about all the projects that the user has.
     *
     * @param userId the user id
     * @return the project
     */
    List <Project> getProject (long userId);

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
     * Getting information about all customers.
     *
     * @param userId the user id
     * @return the customer
     */
    List <Customer> getCustomer (long userId);
}
