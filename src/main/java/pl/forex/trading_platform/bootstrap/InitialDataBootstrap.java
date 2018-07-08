package pl.forex.trading_platform.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.repository.InstrumentRepository;


@Component
@PropertySource("classpath:oandaApi.properties")
public class InitialDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Value("${oanda.instrumentsList}")
    private String[] instrumentsList;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        setInstruments();

    }

    //@Transactional
    private void setInstruments() {
        for (int i = 0; i < instrumentsList.length; i++) {
            instrumentRepository.save(new Instrument(instrumentsList[i]));
        }
    }
}
