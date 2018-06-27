package pl.forex.trading_platform.service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class GetOandaQutes extends Thread {

    private Context context;
    private List<ClientPrice> clientPrices;
    private AccountID accountID;
    private List<String> instruments;
    private int iterator;
    private int qoutesInterval;
    private SimpleDateFormat sdf;

    public GetOandaQutes(Context context, List<ClientPrice> clientPrices, AccountID accountID, List<String> instruments, int iterator, int qoutesInterval) {
        this.context = context;
        this.clientPrices = clientPrices;
        this.accountID = accountID;
        this.instruments = instruments;
        this.iterator = iterator;
        this.qoutesInterval = qoutesInterval;
        this.sdf = new SimpleDateFormat("HH:mm:ss:SSS");
    }

    public void run() {
        try {
            for (; ; ) {
                clientPrices = context.pricing.get(accountID, instruments).getPrices();
                System.out.println(clientPrices);
                System.out.println(clientPrices.get(iterator).getInstrument());
                System.out.println("Real fetch time: " + sdf.format(Calendar.getInstance().getTime()));
                System.out.println(clientPrices.size() + "|current iteration " + iterator);
                System.out.println(clientPrices.get(iterator).getAsks().get(0).getPrice());
                System.out.println(clientPrices.get(iterator).getAsks().get(0).getLiquidity());
                Thread.sleep(qoutesInterval);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
