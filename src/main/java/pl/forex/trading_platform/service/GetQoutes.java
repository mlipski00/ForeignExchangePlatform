package pl.forex.trading_platform.service;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing.ClientPrice;

import java.util.List;

public interface GetQoutes extends Runnable {

    void setSettingsForQuotes(Context context, List<ClientPrice> clientPrices, AccountID accountID, List<String> instruments);

    @Override
    void run();
}
