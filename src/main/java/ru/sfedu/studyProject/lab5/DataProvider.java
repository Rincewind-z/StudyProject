package ru.sfedu.studyProject.lab5;

import ru.sfedu.studyProject.lab5.model.*;

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

    boolean createUrl (URLs urLs);
    boolean editUrl (URLs urLs);
    boolean deleteUrl (URLs urLs);
    Optional<URLs> getUrl (long urlId);

    boolean createFPDetails (FurPartDetails furPartDetails);
    boolean editFPDetails (FurPartDetails furPartDetails);
    boolean deleteFPDetails (FurPartDetails furPartDetails);
    Optional<FurPartDetails> getFPDetails (long furPartDetailsId);
}
