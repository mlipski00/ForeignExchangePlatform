package pl.forex.trading_platform.service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.config.OandaConfig;
import pl.forex.trading_platform.datasources.OandaDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class GetQotesOandaImpl implements GetQotes, Runnable {

    private OandaConfig oandaConfig;

    private Context context;
    private List<ClientPrice> clientPrices;
    private AccountID accountID;
    private List<String> instruments;
    private int iterator;
    private SimpleDateFormat sdf;

    public GetQotesOandaImpl() {
        this.oandaConfig = new OandaConfig();
        System.out.println(oandaConfig.oandaDataSource());
        this.context = oandaConfig.oandaDataSource().getContext();
        this.accountID = oandaConfig.oandaDataSource().getAccountID();
        System.out.println(accountID);
        System.out.println(Arrays.asList(oandaConfig.oandaDataSource().getInstrumentsList()));
        this.instruments = Arrays.asList(oandaConfig.oandaDataSource().getInstrumentsList());
        this.iterator = 1;
        this.sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    }

    public void run() {
        fetchQutesFromApi();
    }

    @Override
    public void fetchQutesFromApi() {
        try {
            clientPrices = context.pricing.get(accountID, instruments).getPrices();
            System.out.println(clientPrices);
            System.out.println(clientPrices.get(iterator).getInstrument());
            System.out.println("Real fetch time: " + sdf.format(Calendar.getInstance().getTime()));
            System.out.println(clientPrices.size() + "|current iteration " + iterator);
            System.out.println(clientPrices.get(iterator).getAsks().get(0).getPrice());
            System.out.println(clientPrices.get(iterator).getAsks().get(0).getLiquidity());
            System.out.println("Active threads: " + Thread.activeCount());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
