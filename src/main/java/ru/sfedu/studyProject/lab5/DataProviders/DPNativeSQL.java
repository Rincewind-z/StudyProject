package ru.sfedu.studyProject.lab5.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sfedu.studyProject.lab5.model.FurPartDetails;
import ru.sfedu.studyProject.lab5.model.Fursuit;
import ru.sfedu.studyProject.lab5.model.Project;
import ru.sfedu.studyProject.utils.HibernateUtil;

import java.util.Optional;

public class DPNativeSQL implements DataProviderLab5 {

    private static final Logger log = LogManager.getLogger(DPNativeSQL.class);

    @Override
    public Optional<Fursuit> getFursuit(long projectId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Fursuit> query = session.createQuery("select pr from lab5_fursuit pr where pr.id=:id", Fursuit.class)
                .setParameter("id", projectId);
        Fursuit fursuit = (Fursuit) query.getSingleResult();
        log.debug(query);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(fursuit);
    }
}
