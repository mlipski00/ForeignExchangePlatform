package pl.forex.trading_platform.service;

public interface LoggerService {

    void logToFile(String message);

    void initNewLogFile();

    void sendLogFileToEmail();
}
