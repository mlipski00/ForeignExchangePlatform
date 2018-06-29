package pl.forex.trading_platform.config;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.datasources.OandaDataSource;
import pl.forex.trading_platform.service.GetQotes;
import pl.forex.trading_platform.service.GetQotesOandaImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GetQotes getQotes;

    @Value("${quoteApi.instrumentsNumber}")
    private int instrumentsNumber;

    @Value("${quoteApi.qoutesInterval}")
    private int qoutesInterval;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        startFetchingQuotes();
    }

    private void startFetchingQuotes() {
        try {

            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(instrumentsNumber);

            for (int i = 0; i < instrumentsNumber; i++) {
                System.out.println(getQotes==null);
                scheduledThreadPoolExecutor.scheduleWithFixedDelay(getQotes, 0, qoutesInterval, TimeUnit.MILLISECONDS);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
