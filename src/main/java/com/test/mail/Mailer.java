package com.test.mail;

import com.test.mail.exceptions.MailerException;

import javax.mail.*;
import javax.mail.search.BodyTerm;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mailer class to connect with the services config in the config.props file
 */
public class Mailer {
    /**
     * Logger for the class
     */
    private static final Logger logger = Logger.getLogger(Mailer.class.getName());
    /**
     * Default mail protocol
     */
    private static final String DEFAULT_PROTOCOL = "imaps";
    /**
     * Default port for the server
     */
    private static final int DEFAULT_PORT = 993;

    /**
     * custom properties for the mailer class
     */
    private Properties properties;

    /**
     * java mail session
     */
    private Session session;

    /**
     * store to get imap mails
     */
    private Store store;

    /**
     * Mail properties config location
     */
    public static final String MAIL_PROPERTIES_CONFIG = "./config/config.props";

    /**
     * Host property name
     */
    public static final String HOST_PROPERTY = "host";

    /**
     * Username property name
     */
    public static final String USERNAME_PROPERTY = "username";

    /**
     * Password property name
     */
    public static final String PASSWORD_PROPERTY = "password";

    /**
     * Protocol property name
     */
    public static final String STORE_PROTOCOL_PROPERTY = "mail.store.protocol";

    /**
     * Folder property name
     */
    public static final String FOLDER_PROPERTY = "folder";

    /**
     * Find property name
     */
    public static final String FIND_PROPERTY = "find";

    /**
     * Port property name
     */
    public static final String PORT_PROPERTY = "port";

    /**
     * default find property value
     */
    public static final String DEFAULT_FIND_PROPERTY = "DevOps";

    /**
     * Load the configuration file and merge the content with the system properties file
     *
     * @return true if the init is success
     */
    public boolean init() {
        boolean result = false;
        Properties systemProperties = System.getProperties();
        properties = new Properties();
        try (InputStream is = Files.newInputStream(Paths.get(MAIL_PROPERTIES_CONFIG))){
            properties.load(is);
            systemProperties.putAll(properties);
            session = Session.getInstance(systemProperties);
            result = true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error init mailer", e);
        }

        return result;
    }

    /**
     * Connect with the mail server, must be call after the init method
     *
     * @return true if connect is successfully
     */
    public boolean connect() {
        boolean result = false;

        try {
            if (properties != null && session != null) {
                store = session.getStore(properties.getProperty(STORE_PROTOCOL_PROPERTY, DEFAULT_PROTOCOL));

                if (!hasConnectProperties()) {
                    throw new MailerException("Invalid mailer configuration");
                }

                store.connect(properties.getProperty(HOST_PROPERTY),
                        getConfigPort(),
                        properties.getProperty(USERNAME_PROPERTY),
                        properties.getProperty(PASSWORD_PROPERTY));

                result = true;
            } else {
                logger.log(Level.INFO, "Mailer not init, please call init first");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error connecting mailer", e);
        }

        return result;
    }

    /**
     * get the message list from the mail, the method do a search on the mail account with the word in the config property "find"
     *
     * @return message list with the find parameter
     * @throws MailerException if an error occurs
     * @throws MessagingException if an error with the mail search occurs
     */
    public Message[] getMessages() throws MailerException, MessagingException {
        if (store == null) {
            throw new MailerException("Error getting messages, call connect first");
        }

        Folder folder;
        if (properties.containsKey(FOLDER_PROPERTY)) {
            folder = store.getFolder(properties.getProperty(FOLDER_PROPERTY));
        } else {
            folder = store.getDefaultFolder();
        }

        folder.open(Folder.READ_ONLY);

        return folder.search(new BodyTerm(properties.getProperty(FIND_PROPERTY, DEFAULT_FIND_PROPERTY)));
    }

    /**
     * disconnect the store if is connected
     *
     * @throws MessagingException if an error closing the store occurs
     */
    public void disconnect() throws MessagingException {
        if (store.isConnected()) {
            store.close();
        }
    }

    /**
     * check if to connect properties are in the config file
     *
     * @return true if the config properties are full config
     */
    private boolean hasConnectProperties() {
        return properties.containsKey(USERNAME_PROPERTY) &&
                properties.containsKey(PASSWORD_PROPERTY) &&
                properties.containsKey(HOST_PROPERTY);
    }

    /**
     * The port configure on the config file
     *
     * @return the config port or the default port if any error occurs
     */
    private int getConfigPort() {
        int result = DEFAULT_PORT;

        try {
            result = Integer.parseInt(properties.getProperty(PORT_PROPERTY));
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid config port using default");
        }

        return result;
    }
}
