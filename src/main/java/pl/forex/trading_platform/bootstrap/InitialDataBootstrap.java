package pl.forex.trading_platform.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.nbp.TableA;
import pl.forex.trading_platform.domain.settings.PlatformSettings;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.InstrumentRepository;
import pl.forex.trading_platform.repository.NbpRatesRepository;
import pl.forex.trading_platform.repository.PlatformSettingsRepository;
import pl.forex.trading_platform.repository.UserRepository;
import pl.forex.trading_platform.service.NbpRates;


@Component
@PropertySource("classpath:oandaApi.properties")
@PropertySource("classpath:platformSettings.properties")
public class InitialDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private PlatformSettingsRepository platformSettingsRepository;

    @Autowired
    private NbpRatesRepository nbpRatesRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${oanda.instrumentsList}")
    private String[] instrumentsList;

    @Value("${platformSettings.decisionTime}")
    private int decisionTime;

    @Value("${platformSettings.nbpTableA}")
    private String nbpTableAurl;

    @Value("${platformSettings.minimumTradeAmount}")
    private Long minimumAmount;

    @Value("${platformSettings.maximumTradeAmount}")
    private Long maximumAmount;

    @Autowired
    NbpRates nbpRates;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        setInstruments();
        setAllSettings();
        setNbpRates();
        mockUsers();
    }
    private void setInstruments() {
        for (int i = 0; i < instrumentsList.length; i++) {
            instrumentRepository.save(new Instrument(instrumentsList[i]));
        }
    }

    private void setAllSettings() {
        platformSettingsRepository.save(
                PlatformSettings.builder()
                .setDecisionTime(decisionTime)
                .setMinimumTradeAumount(minimumAmount)
                .setMaximumTradeAumount(maximumAmount)
                .build()
        );
    }

    private void setNbpRates() {
        TableA[] tableAarray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        nbpRatesRepository.save(tableAarray[0]);
    }

    private void mockUsers() {
        User user = new User();
        user.setUsername("Michał");
        user.setEmail("m@wp.pl");
        user.setBalance(500000);
        user.setBlockedAmount(0);
        user.setActive(true);
        user.setPassword(BCrypt.hashpw("123123", BCrypt.gensalt()));
        userRepository.save(user);
    }
}
