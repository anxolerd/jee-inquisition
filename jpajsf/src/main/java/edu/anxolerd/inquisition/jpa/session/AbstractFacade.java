package edu.anxolerd.inquisition.jpa.session;


import edu.anxolerd.inquisition.jpa.PersistenceUtil;

import javax.persistence.EntityManager;


public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        PersistenceUtil.getEntityManager().persist(entity);
    }

    public T edit(T entity) {
        return PersistenceUtil.getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        PersistenceUtil.getEntityManager().remove(PersistenceUtil.getEntityManager().merge(entity));
    }
}
