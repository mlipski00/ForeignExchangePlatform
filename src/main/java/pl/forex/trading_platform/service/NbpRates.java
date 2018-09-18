package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.nbp.TableA;

public interface NbpRates {

    String getTableAQuotesString(String url);

    TableA[] getTableAQuotesArray(String url);
}
