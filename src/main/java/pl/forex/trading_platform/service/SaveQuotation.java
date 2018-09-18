package pl.forex.trading_platform.service;

import com.oanda.v20.pricing.ClientPrice;

public interface SaveQuotation {

    void saveQuotation(ClientPrice clientPrice);
}
