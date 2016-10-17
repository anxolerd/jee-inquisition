package edu.anxolerd.inquisition.jpa.session;


import edu.anxolerd.inquisition.jpa.PersistenceUtil;

import javax.persistence.EntityManager;


public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return PersistenceUtil.getEntityManager();
    };

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T edit(T entity) {
        return getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}
