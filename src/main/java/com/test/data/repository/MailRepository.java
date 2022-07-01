package com.test.data.repository;

import com.test.data.models.Mail;
import com.test.data.util.SessionFactoryUtil;
import org.hibernate.Session;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mail repository to access the database
 */
public class MailRepository {
    /**
     * Log for the class
     */
    private static final Logger logger = Logger.getLogger(MailRepository.class.getName());

    /**
     * Create a Mail record on the database
     *
     * @param mail record to be saved on the database
     */
    public void create(Mail mail) {
        try (Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(mail);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving mail data ", e);
        }
    }

    /**
     * find a mail in the database by id
     *
     * @param id message id to find on the database
     *
     * @return the mail in the database or null if is not found
     */
    public Mail find(String id) {
        Mail result = null;

        try (Session session = SessionFactoryUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            result = session.find(Mail.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting mail data ", e);
        }

        return result;
    }
}
