package com.test.data.models;

import java.util.Date;

/**
 * Mail model entity to connect with the database
 */
public class Mail {
    /**
     * ID for the mail message
     */
    private String id;

    /**
     * Message sent date
     */
    private Date sentDate;

    /**
     * Message subject
     */
    private String subject;

    /**
     * Message from mail address
     */
    private String toMail;

    /**
     * @return the id for the mail
     */
    public String getId() { return id; }

    /**
     * set the id for the message
     *
     * @param id id for the message
     */
    public void setId(String id) { this.id = id; }

    /**
     * @return send date of the message
     */
    public Date getSentDate() { return sentDate; }

    /**
     * set sent date of the message
     *
     * @param sentDate sent date of the message
     */
    public void setSentDate(Date sentDate) { this.sentDate = sentDate; }

    /**
     * @return the subject of the message
     */
    public String getSubject() { return subject; }

    /**
     * set the subject
     *
     * @param subject subject for the message
     */
    public void setSubject(String subject) { this.subject = subject; }

    /**
     * @return from mail of the message
     */
    public String getToMail() { return toMail; }

    /**
     * set from mail for the message
     *
     * @param toMail from mail of the message
     */
    public void setToMail(String toMail) { this.toMail = toMail; }
}
