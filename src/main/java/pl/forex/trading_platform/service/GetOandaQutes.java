package pl.forex.trading_platform.service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.repository.InstrumentRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class GetOandaQutes implements Runnable {

    private Context context;
    private List<ClientPrice> clientPrices;
    private AccountID accountID;
    private List<String> instruments;
    private SimpleDateFormat sdf;

    public GetOandaQutes(Context context, List<ClientPrice> clientPrices, AccountID accountID, List<String> instruments) {
        this.context = context;
        this.clientPrices = clientPrices;
        this.accountID = accountID;
        this.instruments = instruments;
        this.sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    }

    public void run() {
        try {
            clientPrices = context.pricing.get(accountID, instruments).getPrices();
            System.out.println(clientPrices);
            System.out.println(clientPrices.get(0).getInstrument());
            System.out.println("Real fetch time: " + sdf.format(Calendar.getInstance().getTime()));
            System.out.println(clientPrices.size() + "|current iteration " + instruments.get(0));
            System.out.println(clientPrices.get(0).getAsks().get(0).getPrice());
            System.out.println(clientPrices.get(0).getAsks().get(0).getLiquidity());
            System.out.println("Active threads: " + Thread.activeCount());

            SaveQuotation saveQuotation = new SaveQuotationImpl();
            saveQuotation.saveQuotation(clientPrices.get(0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
