package edu.anxolerd.jee.lab4.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PersistenceTools {
    private static final Logger LOG = LogManager.getLogger(PersistenceTools.class);
    private static EntityManager em;
    private static EntityManagerFactory emf;

    private PersistenceTools() {};

    private static EntityManagerFactory getEntitiyManagerFactory() {
        if (emf == null) {
            InputStream input = null;
            Properties props = new Properties();
            try {
                input = new FileInputStream("/tmp/hibernate.properties");
                props.load(input);
            } catch (Exception e) {
                LOG.error(e);
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
            emf = Persistence.createEntityManagerFactory("edu.anxolerd.jee.lab4", props);
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        if (em == null) {
            em = getEntitiyManagerFactory().createEntityManager();
        }
        return em;
    }
}
