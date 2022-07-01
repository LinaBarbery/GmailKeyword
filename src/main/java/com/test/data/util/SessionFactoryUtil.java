package com.test.data.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Session factory util to access the database
 */
public class SessionFactoryUtil {
    /**
     * logger for the class
     */
    private static final Logger logger = Logger.getLogger(SessionFactoryUtil.class.getName());

    /**
     * Session factory to access the database
     */
    private static final SessionFactory sessionFactory;

    /**
     * config file path location
     */
    private static final String CONFIG_FILE_PATH = "./config/hibernate.xml";

    static {
        try {
            sessionFactory = new Configuration().configure(new File(CONFIG_FILE_PATH))
                    .buildSessionFactory();
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "Error configuration hibernate");
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * @return the session factory to access the database
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * to prevent instances
     */
    private SessionFactoryUtil() {}
}
