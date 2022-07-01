package com.test.mail.util;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.UUID;

/**
 * Utility dor Mail messages
 */
public class MailUtil {
    /**
     * Default email from if the from address is not valid
     */
    public static final String DEFAULT_EMAIL_FROM = "no_email_from";
    /**
     * Message id header name
     */
    public static final String MESSAGE_ID_HEADER_NAME = "Message-ID";

    /**
     * Return te Message-ID header value or a random UUID if the header is not found
     * @param message message to inspect
     * @return the value for the message id
     */
    public static String getFirstMessageId(Message message) {
        String result;

        try {
            String[] messageId = message.getHeader(MESSAGE_ID_HEADER_NAME);
            result = messageId.length > 0 ? messageId[0]:UUID.randomUUID().toString();
        } catch (MessagingException e) {
            result = UUID.randomUUID().toString();
        }

        return result;
    }

    /**
     * return the first address from for the message
     *
     * @param message message to inspect
     * @return the value for the first from address
     */
    public static String getFirstFrom(Message message) {
        String result;

        try {
            Address[] addresses = message.getFrom();
            result = addresses.length > 0 ? addresses[0].toString():DEFAULT_EMAIL_FROM;
        } catch (MessagingException e) {
            result = DEFAULT_EMAIL_FROM;
        }

        return result;
    }

    /**
     * To prevent instances
     */
    private MailUtil() {}
}
