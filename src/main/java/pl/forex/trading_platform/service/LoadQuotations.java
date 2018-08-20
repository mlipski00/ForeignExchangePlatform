package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.AskPriceBucket;
import pl.forex.trading_platform.domain.BidPriceBucket;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;

import java.util.List;

public interface LoadQuotations {

    List<Quotation> loadAllQuotations();

    List<AskPriceBucket> loadAllAskPrices();

    List<BidPriceBucket> loadAllBidPrices();

    List<Instrument> loadAllInstruments();

    List<Quotation> loadLastQuotations();
}
