package ru.sfedu.studyProject.lab2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.studyProject.lab1.Constants;
import ru.sfedu.studyProject.utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class HibernateDataProvider {

    public static void create(TestEntity testEntity){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(testEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static Optional<TestEntity> get(Long id){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        TestEntity testEntity = session.get(TestEntity.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(testEntity);
    }

    public static void update(TestEntity testEntity){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(testEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(TestEntity testEntity){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(testEntity);
        session.getTransaction().commit();
        session.close();
    }
}
