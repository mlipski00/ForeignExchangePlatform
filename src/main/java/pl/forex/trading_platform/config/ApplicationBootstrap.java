package pl.forex.trading_platform.config;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Component
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
            List<ClientPrice> clientPrices;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
            for (; ; ) {
                clientPrices = context.pricing.get(accountID, instruments).getPrices();
                System.out.println(clientPrices);
                System.out.println("sprawdzamy czy asychronicznie " + sdf.format(Calendar.getInstance().getTime()));
                System.out.println(clientPrices.size());
                System.out.println(clientPrices.get(0).getAsks().get(0).getPrice());
                System.out.println(clientPrices.get(0).getAsks().get(0).getLiquidity());
                System.out.println(clientPrices.get(0).getBids());
                System.out.println(clientPrices.get(0).getTime());
                System.out.println(clientPrices.get(0).getStatus());
                System.out.println("qoutesInterval: " + qoutesInterval);
                Thread.sleep(qoutesInterval);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
