package edu.anxolerd.jee.lab4.services;


import edu.anxolerd.jee.lab4.entities.Passport;
import edu.anxolerd.jee.lab4.entities.Sin;
import edu.anxolerd.jee.lab4.entities.Sinner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class SinService {
    private static final Logger LOG = LogManager.getLogger(PersonService.class.getName());

    public SinService() {
    }

    public List<Sin> getAll() {
        EntityManager em = PersistenceTools.getEntityManager();
        Query q = em.createQuery("SELECT sin FROM Sin sin", Sin.class);
        return q.getResultList();
    }

    public Sin getById(long id) {
        EntityManager em = PersistenceTools.getEntityManager();
        return em.find(Sin.class, id);
    }

    public void update(Sin s) {
        EntityManager em = PersistenceTools.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(s);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOG.error(e);
            throw e;
        }
    }

    public void create(Sin s) {
        EntityManager em = PersistenceTools.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(s);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOG.error(e);
            throw e;
        }
    }

}
