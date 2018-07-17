package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.domain.AskPriceBucket;
import pl.forex.trading_platform.domain.BidPriceBucket;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.service.LoadQuotations;
import pl.forex.trading_platform.service.NbpRates;

import java.util.List;

@RestController
@RequestMapping("/quotations")
public class QuotationsRestConstroller {

    @Autowired
    LoadQuotations loadQuotations;

    @Autowired
    NbpRates nbpRates;

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

    @RequestMapping("/nbp")
    public String getNbpRates() {
        return nbpRates.getTableAQuotes("http://api.nbp.pl/api/exchangerates/tables/A/");
    }
}
