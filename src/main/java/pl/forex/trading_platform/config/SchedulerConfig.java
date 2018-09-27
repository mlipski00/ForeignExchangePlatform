package pl.forex.trading_platform.config;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SchedulerConfig {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    LoadQuotations loadQuotations;

    @Value("${platformSettings.nbpTableA}")
    private String nbpTableAurl;

    @Autowired
    NbpRates nbpRates;

    @Autowired
    private NbpRatesRepository nbpRatesRepository;

    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages() {
        System.out.println(loadQuotations.loadLastQuotations());
        simpMessagingTemplate.convertAndSend("/topic/user", loadQuotations.loadLastQuotations());
    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    public void SaveDailyNbpRates() {
        TableA[] tableAarray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        nbpRatesRepository.save(tableAarray[0]);

    }
}

