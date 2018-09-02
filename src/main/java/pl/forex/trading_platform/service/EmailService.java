package pl.forex.trading_platform.service;

import java.util.logging.FileHandler;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendEmailWithAttachment(String logFileName);
}
