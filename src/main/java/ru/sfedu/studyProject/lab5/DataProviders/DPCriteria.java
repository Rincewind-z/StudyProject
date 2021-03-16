package ru.sfedu.studyProject.lab5.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sfedu.studyProject.lab5.model.Fursuit;
import ru.sfedu.studyProject.lab5.model.Project;
import ru.sfedu.studyProject.utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class DPCriteria implements DataProviderLab5 {

    private static final Logger log = LogManager.getLogger(DPCriteria.class);

    @Override
    public Optional<Fursuit> getFursuit(long projectId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Fursuit> criteriaQuery = criteriaBuilder.createQuery(Fursuit.class);
        Root<Fursuit> projectRoot = criteriaQuery.from(Fursuit.class);
        criteriaQuery.select(projectRoot).where(criteriaBuilder.equal(projectRoot.get("id"), projectId));
        Fursuit project = session.createQuery(criteriaQuery).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(project);
    }
}
