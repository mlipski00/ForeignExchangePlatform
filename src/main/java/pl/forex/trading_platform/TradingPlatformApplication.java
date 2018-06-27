package pl.forex.trading_platform;

import com.oanda.v20.Request;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingBasePricesRequest;
import com.oanda.v20.pricing.PricingContext;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.primitives.AcceptDatetimeFormat;
import com.oanda.v20.primitives.DateTime;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TradingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingPlatformApplication.class, args);

        List<String> instruments = new ArrayList<>(
                Arrays.asList("EUR_USD"));

        Context context = new Context("https://api-fxpractice.oanda.com/", "25c688234329e1cffa2142fe3f464edc-5a1491707829b49fe90362ae8e59bfc9", "", AcceptDatetimeFormat.RFC3339, HttpClients.createDefault());


        try {
            List<ClientPrice> result;
            for (; ; ) {
                result = context.pricing.get(new AccountID("101-004-8649412-001"), instruments).getPrices();
                System.out.println(result);
                Thread.sleep(10000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
