package edu.anxolerd.jee.lab4.services;


import edu.anxolerd.jee.lab4.entities.Passport;
import edu.anxolerd.jee.lab4.entities.Sinner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class PersonService {
    private static final Logger LOG = LogManager.getLogger(PersonService.class.getName());
    public PersonService() {}

    public List<Sinner> getAll() {
        EntityManager em = PersistenceTools.getEntityManager();
        Query q = em.createQuery("SELECT sinner FROM Sinner sinner", Sinner.class);
        return q.getResultList();
    }

    public Sinner getById(long id) {
        EntityManager em = PersistenceTools.getEntityManager();
        return em.find(Sinner.class, id);
    }

    public void update(Sinner s) {
        Passport p = s.getPassport();
        if (p.getSinner() == null) {
            p.setSinner(s);
        }
        EntityManager em = PersistenceTools.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(s);
            em.merge(s.getPassport());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOG.error(e);
            throw e;
        }
    }

    public void create(Sinner s) {
        Passport p = s.getPassport();
        if (p.getSinner() == null) {
            p.setSinner(s);
        }

        EntityManager em = PersistenceTools.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(s);
            em.persist(s.getPassport());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            LOG.error(e);
            throw e;
        }
    }
}
