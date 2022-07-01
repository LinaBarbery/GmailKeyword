package com.test;

import com.test.data.models.Mail;
import com.test.data.repository.MailRepository;
import com.test.mail.Mailer;
import com.test.mail.util.MailUtil;

import javax.mail.Message;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application entry point
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    /**
     * Main method
     *
     * @param args arguments for the application
     */
    public static void main(String[] args) {
        try {
            Mailer mailer = new Mailer();
            MailRepository mailRepository = new MailRepository();

            if (mailer.init() && mailer.connect()) {
                Message[] messages = mailer.getMessages();

                for (Message message:messages) {
                    Mail mail = new Mail();
                    mail.setId(MailUtil.getFirstMessageId(message));
                    mail.setSentDate(message.getSentDate());
                    mail.setToMail(MailUtil.getFirstFrom(message));
                    mail.setSubject(message.getSubject());

                    if (mailRepository.find(mail.getId()) == null) {
                        mailRepository.create(mail);
                    }
                }
            }

            mailer.disconnect();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error running app... ", e);
        }
    }
}
