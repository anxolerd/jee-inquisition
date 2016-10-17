package edu.anxolerd.inquisition.services;


import edu.anxolerd.inquisition.jpa.PersistenceUtil;
import edu.anxolerd.inquisition.jpa.entities.Interest;
import edu.anxolerd.inquisition.jpa.session.InterestFacade;

import javax.faces.bean.ManagedBean;


@ManagedBean(name = "InterestService")
public class InterestService {

    public InterestService() {
    }

    public void create() {
        Interest interest = new Interest();
        interest.setTitle("Test 1")
            .setDescription("Testing JPA")
            .setSinRate(20);

        InterestFacade interestFacade = new InterestFacade();
        try {
            PersistenceUtil.getEntityManager().getTransaction().begin();
            interestFacade.create(interest);
            PersistenceUtil.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            PersistenceUtil.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }
}
