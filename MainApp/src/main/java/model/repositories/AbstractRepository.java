package model.repositories;

import model.entities.AbstractEntity;
import model.log.ModelLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractRepository<T extends AbstractEntity> implements CRUDRepository<T> {
    private SessionFactory sessionFactory;
    private Class<T> classEntity;

    private static final String GET_ALL = "FROM %s";
    private static final String GET_BY_PROPERTY = "FROM %s e where e.%s = :val";
    private static final String GET_ALL_VALID = "SELECT e FROM %s e where e.valid = 1";

    /**
     * Constructor
     *
     * @param classEntity defines entity to be used in DAO
     */
    public AbstractRepository(Class<T> classEntity) {
        this.classEntity = classEntity;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Class<T> getClassEntity() {
        return classEntity;
    }

    @SuppressWarnings("unchecked")
    @ModelLog
    public List<T> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(String.format(GET_ALL, classEntity.getSimpleName())).list();
    }

    @SuppressWarnings("unchecked")
    @ModelLog
    public List<T> getAllValid() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(String.format(GET_ALL_VALID, classEntity.getSimpleName())).list();
    }

    @ModelLog
    public void create(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
        session.save(entity);
    }

    @ModelLog
    public void update(T obj) {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.saveOrUpdate(obj);
    }

    @SuppressWarnings("unchecked")
    @ModelLog
    public T getByProperty(String property, Object value) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(String.format(GET_BY_PROPERTY, classEntity.getSimpleName(),
                property));
        query.setParameter("val", value);
        List<T> result = query.list();
        return result.isEmpty() ? null : result.get(0);
    }

}
