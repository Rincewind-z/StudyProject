package ru.sfedu.studyProject.lab1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.studyProject.utils.HibernateUtil;

import java.util.Arrays;
import java.util.List;

public class HibernateDataProvider {
    private static final Logger log = LogManager.getLogger(HibernateDataProvider.class);

    public static void getHelp() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Object> list = session.createSQLQuery(Constants.SELECT_FROM_HELP).list();
        list.forEach(object -> log.info(object.toString()));
        session.close();
    }

    public static void getSchemata() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Object[]> list = session.createSQLQuery(Constants.SELECT_FROM_SCHEMATA).list();
        list.forEach(objects ->
                Arrays.stream(objects).forEach(object -> log.info(object.toString())));
        session.close();
    }

    public static void getSettings() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Object> list = session.createSQLQuery(Constants.SELECT_FROM_SETTINGS).list();
        list.forEach(object -> log.info(object.toString()));
        session.close();
    }

    public static void getTypeInfo() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Object> list = session.createSQLQuery(Constants.SELECT_FROM_TYPE_INFO).list();
        list.forEach(object -> log.info(object.toString()));
        session.close();
    }
}
