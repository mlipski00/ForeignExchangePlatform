package pl.forex.trading_platform.service;

public interface GetQotes extends Runnable {

    void fetchQutesFromApi();
}
