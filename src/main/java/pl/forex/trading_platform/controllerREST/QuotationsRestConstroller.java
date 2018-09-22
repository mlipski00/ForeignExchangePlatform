package pl.forex.trading_platform.controllerREST;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.domain.AskPriceBucket;
import pl.forex.trading_platform.domain.BidPriceBucket;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.service.LoadQuotations;
import pl.forex.trading_platform.service.NbpRates;
import pl.forex.trading_platform.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/quotations")
public class QuotationsRestConstroller {

    @Autowired
    LoadQuotations loadQuotations;

    @Autowired
    NbpRates nbpRates;

    @Autowired
    private UserService userService;

    final static Logger logger = Logger.getLogger(QuotationsRestConstroller.class);

    @RequestMapping("/all")
    public List<Quotation> getQoutations() {
        logger.debug("User: " + userService.getLoggedUser());
        return loadQuotations.loadAllQuotations();
    }

    @RequestMapping("/ask")
    public List<AskPriceBucket> getAskPrices() {

        logger.debug("User: " + userService.getLoggedUser());
        return loadQuotations.loadAllAskPrices();
    }

    @RequestMapping("/bid")
    public List<BidPriceBucket> getBidPrices() {

        logger.debug("User: " + userService.getLoggedUser());
        return loadQuotations.loadAllBidPrices();
    }

    @RequestMapping("/nbp")
    public String getNbpRates() {
        logger.debug("User: " + userService.getLoggedUser());
        return nbpRates.getTableAQuotesString("http://api.nbp.pl/api/exchangerates/tables/A/");
    }
}
