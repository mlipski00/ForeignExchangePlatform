package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.domain.AskPriceBucket;
import pl.forex.trading_platform.domain.BidPriceBucket;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.service.LoadQuotations;

import java.util.List;

@RestController
@RequestMapping("/quotations")
public class QuotationsRestConstroller {

    @Autowired
    LoadQuotations loadQuotations;

    @RequestMapping("/all")
    public List<Quotation> getQoutations() {
        return loadQuotations.loadAllQuotations();
    }

    @RequestMapping("/ask")
    public List<AskPriceBucket> getAskPrices() {
        return loadQuotations.loadAllAskPrices();
    }
    @RequestMapping("/bid")
    public List<BidPriceBucket> getBidPrices() {
        return loadQuotations.loadAllBidPrices();
    }
}
