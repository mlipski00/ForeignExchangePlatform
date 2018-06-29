package pl.forex.trading_platform.config;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.service.GetOandaQutes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:oandaApi.properties")
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${oanda.accountIDValue}")
    private String accountIDValue;

    @Value("${oanda.token}")
    private String oandaToken;

    @Value("${oanda.instrumentsList}")
    private String[] instrumentsList;

    @Value("${oanda.apiURI}")
    private String apiURI;

    @Value("${oanda.getQoutesInterval}")
    private int qoutesInterval;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        startFetchingQuotes();
    }

    private void startFetchingQuotes() {
        AccountID accountID = new AccountID(accountIDValue);

        List<String> instruments = new ArrayList<>(
                Arrays.asList(instrumentsList));

        Context context = new Context(apiURI, oandaToken, "", AcceptDatetimeFormat.RFC3339, HttpClients.createDefault());

        try {
            List<ClientPrice> clientPrices = context.pricing.get(accountID, instruments).getPrices();

            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(clientPrices.size());

            for (int i = 0; i < clientPrices.size(); i++) {
                scheduledThreadPoolExecutor.scheduleWithFixedDelay(new GetOandaQutes(context, clientPrices, accountID, instruments, i), 0, qoutesInterval, TimeUnit.MILLISECONDS);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
