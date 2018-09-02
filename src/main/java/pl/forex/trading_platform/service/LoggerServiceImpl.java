package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class LoggerServiceImpl implements LoggerService {

    @Autowired
    private EmailService emailService;

    private static String currentLogFileName;

    private final Logger logger = Logger.getLogger(LoggerServiceImpl.class
            .getName());
    private FileHandler fh = null;

    @Override
    public void initNewLogFile() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
            currentLogFileName = "MyLogFile_"
                    + format.format(Calendar.getInstance().getTime()) + ".log";
            fh = new FileHandler(currentLogFileName);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendLogFileToEmail() {
        emailService.sendEmailWithAttachment(currentLogFileName);
    }

    @Override
    public void logToFile(String message) {
        logger.info(message);
}
}
