package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.nbp.TableA;

public interface NbpRates {

    public String getTableAQuotesString(String url);

    public TableA[] getTableAQuotesArray(String url);
}
