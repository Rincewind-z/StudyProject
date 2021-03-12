package ru.sfedu.studyProject.lab3.SingleTable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.studyProject.lab3.SingleTable.model.*;
import ru.sfedu.studyProject.utils.HibernateUtil;

import java.util.Optional;

public class HibernateDataProvider implements DataProvider {
    @Override
    public boolean createMaterial(Material material) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(material);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean editMaterial(Material editMaterial) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(editMaterial);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteMaterial(Material material) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(material);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Optional<Material> getMaterial(long materialId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Material material = session.get(Material.class, materialId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(material);
    }

    @Override
    public boolean createProject(Project project) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(project);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean editProject(Project editProject) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(editProject);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteProject(Project project) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(project);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Optional<Project> getProject(Class<?> clazz, long projectId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project project = (Project) session.get(clazz, projectId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(project);
    }

    @Override
    public boolean createFursuitPart(FursuitPart fursuitPart) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(fursuitPart);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean editFursuitPart(FursuitPart editFursuitPart) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(editFursuitPart);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteFursuitPart(FursuitPart fursuitPart) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(fursuitPart);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Optional<FursuitPart> getFursuitPart(long fursuitPartId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        FursuitPart fursuitPart = session.get(FursuitPart.class, fursuitPartId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(fursuitPart);
    }

    @Override
    public boolean createCustomer(Customer customer) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean editCustomer(Customer editCustomer) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(editCustomer);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(customer);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Optional<Customer> getCustomer(long customerId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, customerId);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(customer);
    }
}
