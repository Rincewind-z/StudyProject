package ru.sfedu.studyProject.lab3.TablePerClass;

import ru.sfedu.studyProject.lab3.TablePerClass.model.*;

import java.util.Optional;

public interface DataProvider {
    boolean createMaterial (Material material);
    boolean editMaterial (Material editMaterial);
    boolean deleteMaterial (Material material);
    Optional<Material> getMaterial (long materialId);

    boolean createProject (Project project);
    boolean editProject (Project editProject);
    boolean deleteProject (Project project);
    Optional<Project> getProject (Class<?> clazz, long projectId);

    boolean createFursuitPart (FursuitPart fursuitPart);
    boolean editFursuitPart (FursuitPart editFursuitPart);
    boolean deleteFursuitPart (FursuitPart fursuitPart);
    Optional<FursuitPart> getFursuitPart (long fursuitPartId);

    boolean createCustomer (Customer customer);
    boolean editCustomer (Customer editCustomer);
    boolean deleteCustomer (Customer customer);
    Optional<Customer> getCustomer (long customerId);
}
