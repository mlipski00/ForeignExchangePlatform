package pl.forex.trading_platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.service.LoadQuotations;

import java.util.List;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    LoadQuotations loadQuotations;

    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages() {
        List<Quotation> quotations = loadQuotations.loadAllQuotations();
        List<Instrument> instruments = loadQuotations.loadAllInstruments();
        simpMessagingTemplate.convertAndSend("/topic/user", quotations.subList(Math.max(quotations.size() - instruments.size(), 0), quotations.size()));
    }
}

