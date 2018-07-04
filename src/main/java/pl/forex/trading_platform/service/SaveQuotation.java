package pl.forex.trading_platform.service;

import com.oanda.v20.pricing.ClientPrice;

public interface SaveQuotation {

    public void saveQuotation(ClientPrice clientPrice);
}
