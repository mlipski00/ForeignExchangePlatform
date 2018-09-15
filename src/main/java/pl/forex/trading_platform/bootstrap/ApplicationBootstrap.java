package pl.forex.trading_platform.bootstrap;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.service.GetQoutes;
import pl.forex.trading_platform.service.GetQuotesOandaImpl;
import pl.forex.trading_platform.service.LoggerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
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

    @Autowired
    private ObjectFactory<GetQoutes> getQoutes;

    @Autowired
    private LoggerService loggerService;

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

            ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(clientPrices.size());

            for (int i = 0; i < clientPrices.size(); i++) {
                GetQoutes getQoutesInstance = getQoutes.getObject();
                getQoutesInstance.setSettingsForQuotes(context, clientPrices, accountID, Arrays.asList(instruments.get(i)));
                scheduledExecutorService.scheduleWithFixedDelay(getQoutesInstance, 0, qoutesInterval, TimeUnit.MILLISECONDS);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
