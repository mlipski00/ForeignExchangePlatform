package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.Quotation;

import java.util.List;

public interface JsonConverter {

    String Quotation2JsonConverter(List<Quotation> quotationList);
}
