package pl.forex.trading_platform.service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Component
@NoArgsConstructor
@Setter
@Getter
@Scope("prototype")
public class GetQuotesOandaImpl implements GetQoutes {

    @Autowired
    private SaveQuotation saveQuotation;

    private Context context;
    private List<ClientPrice> clientPrices;
    private AccountID accountID;
    private List<String> instruments;
    private SimpleDateFormat sdf;


    public SaveQuotation getSaveQuotation() {
        return saveQuotation;
    }

    public void setSaveQuotation(SaveQuotation saveQuotation) {
        this.saveQuotation = saveQuotation;
    }

    @Override
    public void setSettingsForQuotes(Context context, List<ClientPrice> clientPrices, AccountID accountID, List<String> instruments) {
        this.context = context;
        this.clientPrices = clientPrices;
        this.accountID = accountID;
        this.instruments = instruments;
        this.sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    }
    @Override
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

            saveQuotation.saveQuotation(clientPrices.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
