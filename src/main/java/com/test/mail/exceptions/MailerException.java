package com.test.mail.exceptions;

/**
 * Mailer custom exception
 */
public class MailerException extends Exception {
    public MailerException() {
        super();
    }

    public MailerException(String message) {
        super(message);
    }
}
