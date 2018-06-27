package pl.forex.trading_platform.config;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import com.oanda.v20.primitives.Instrument;
import org.apache.http.impl.client.HttpClients;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        startFetchingQuotes();
    }

    private void startFetchingQuotes() {
        Instrument instrument = new Instrument();
        instrument.setName("EUR_USD");
        instrument.setDisplayName("EUR_USD");
        AccountID accountID = new AccountID("101-004-8649412-001");

        List<String> instruments = new ArrayList<>(
                Arrays.asList("EUR_USD"));

        Context context = new Context("https://api-fxpractice.oanda.com/", "25c688234329e1cffa2142fe3f464edc-5a1491707829b49fe90362ae8e59bfc9", "", AcceptDatetimeFormat.RFC3339, HttpClients.createDefault());


        try {
            List<ClientPrice> clientPrices;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
            for (; ; ) {
                clientPrices = context.pricing.get(accountID, instruments).getPrices();
                System.out.println(clientPrices);
                System.out.println("sprawdzamy czy asychronicznie " + sdf.format(Calendar.getInstance().getTime()));
                System.out.println(clientPrices.get(0).getAsks().get(0).getPrice());
                System.out.println(clientPrices.get(0).getAsks().get(0).getLiquidity());
                System.out.println(clientPrices.get(0).getBids());
                System.out.println(clientPrices.get(0).getTime());
                System.out.println(clientPrices.get(0).getStatus());

                Thread.sleep(10000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
