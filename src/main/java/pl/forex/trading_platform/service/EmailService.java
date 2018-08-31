package pl.forex.trading_platform.service;

public interface EmailService {

    public void sendEmail(String to, String subject, String text);
}
