package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.Quotation;

import java.util.Set;

public interface LoadQuotations {

    Set<Quotation> loadQuotations(Long instrumentID);
}
