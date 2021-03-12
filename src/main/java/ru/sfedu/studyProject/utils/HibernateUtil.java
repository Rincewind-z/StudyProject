package ru.sfedu.studyProject.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.studyProject.core.Constants;
import ru.sfedu.studyProject.lab2.TestEntity;
import ru.sfedu.studyProject.lab3.MappedSuperclass.model.*;

import java.io.File;

public class HibernateUtil {

    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/hibernate.cfg.xml";

    private static SessionFactory sessionFactory;
    /**
     * Создание фабрики
     *
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            File nf;
            if (System.getProperty(Constants.CONFIG_PATH) != null) {
                nf = new File(System.getProperty(Constants.CONFIG_PATH));
            } else {
                nf = new File(DEFAULT_CONFIG_PATH);
            }

            // loads configuration and mappings
            Configuration configuration = new Configuration().configure(nf);
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            MetadataSources metadataSources =
                    new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(TestEntity.class);

            metadataSources.addAnnotatedClass(Material.class);
            metadataSources.addAnnotatedClass(Customer.class);
            metadataSources.addAnnotatedClass(Fursuit.class);
            metadataSources.addAnnotatedClass(Art.class);
            metadataSources.addAnnotatedClass(Toy.class);
            metadataSources.addAnnotatedClass(FursuitPart.class);
            metadataSources.addAnnotatedClass(Project.class);

            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.Material.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.Customer.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.Fursuit.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.Art.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.Toy.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.FursuitPart.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.TablePerClass.model.Project.class);

            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.Material.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.Customer.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.Fursuit.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.Art.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.Toy.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.FursuitPart.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.SingleTable.model.Project.class);

            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.Material.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.Customer.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.Fursuit.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.Art.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.Toy.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.FursuitPart.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab3.JoinedTable.model.Project.class);

            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.Material.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.Customer.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.Fursuit.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.Art.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.Toy.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.FursuitPart.class);
            metadataSources.addAnnotatedClass(ru.sfedu.studyProject.lab4.model.Project.class);

            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        }
        return sessionFactory;
    }
}