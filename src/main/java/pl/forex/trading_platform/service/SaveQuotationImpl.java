package pl.forex.trading_platform.service;

import com.oanda.v20.pricing.ClientPrice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.AskPriceBucket;
import pl.forex.trading_platform.domain.BidPriceBucket;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.repository.AskPriceBucketDao;
import pl.forex.trading_platform.repository.BidPriceBucketDao;
import pl.forex.trading_platform.repository.InstrumentDao;
import pl.forex.trading_platform.repository.QuotationDao;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Getter
@Setter
@Transactional
public class SaveQuotationImpl implements SaveQuotation {

    @Autowired
    private InstrumentDao instrumentDao;

    @Autowired
    private AskPriceBucketDao askPriceBucketDao;

    @Autowired
    private BidPriceBucketDao bidPriceBucketDao;

    @Autowired
    private QuotationDao quotationDao;

    public SaveQuotationImpl() {
    }

    @Override
    public void saveQuotation(ClientPrice clientPrice) {
        try {
            Instrument instrumentToSave = null;
            Optional<Instrument> optionalInstrument = Optional.ofNullable(new Instrument(String.valueOf(clientPrice.getInstrument())));
            if (optionalInstrument.isPresent()) {
                instrumentToSave = instrumentDao.findByDescription(clientPrice.getInstrument().toString());
            }
            AskPriceBucket askPriceBucket = new AskPriceBucket(Double.valueOf(String.valueOf(clientPrice.getAsks().get(0).getPrice())), Long.valueOf(String.valueOf(clientPrice.getAsks().get(0).getLiquidity())));
            BidPriceBucket bidPriceBucket = new BidPriceBucket(Double.valueOf(String.valueOf(clientPrice.getBids().get(0).getPrice())), Long.valueOf(String.valueOf(clientPrice.getBids().get(0).getLiquidity())));

            Quotation quotation = new Quotation(LocalDateTime.now(), instrumentToSave, bidPriceBucket, askPriceBucket);
            quotationDao.save(quotation);
            bidPriceBucket.setQuotation(quotation);
            bidPriceBucketDao.save(bidPriceBucket);
            askPriceBucket.setQuotation(quotation);
            askPriceBucketDao.save(askPriceBucket);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}