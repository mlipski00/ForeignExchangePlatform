package pl.forex.trading_platform.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.forex.trading_platform.domain.nbp.TableA;
import pl.forex.trading_platform.repository.NbpRatesRepository;
import pl.forex.trading_platform.service.LoadQuotations;
import pl.forex.trading_platform.service.NbpRates;

@EnableScheduling
@Configuration
@PropertySource("classpath:platformSettings.properties")
@Log4j2
public class SchedulerConfig {

    private SimpMessagingTemplate simpMessagingTemplate;

    private LoadQuotations loadQuotations;

    private NbpRates nbpRates;

    private NbpRatesRepository nbpRatesRepository;

    @Value("${platformSettings.nbpTableA}")
    private String nbpTableAurl;

    public SchedulerConfig(SimpMessagingTemplate simpMessagingTemplate, LoadQuotations loadQuotations, NbpRates nbpRates, NbpRatesRepository nbpRatesRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.loadQuotations = loadQuotations;
        this.nbpRates = nbpRates;
        this.nbpRatesRepository = nbpRatesRepository;
    }

    //todo switch to websockets
    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages() {
        log.info(loadQuotations.loadLastQuotations());
        simpMessagingTemplate.convertAndSend("/topic/user", loadQuotations.loadLastQuotations());
    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void SaveDailyNbpRates() {
        TableA[] tableArray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        nbpRatesRepository.save(tableArray[0]);

    }
}

