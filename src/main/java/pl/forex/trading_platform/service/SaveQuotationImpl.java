package pl.forex.trading_platform.service;

import com.oanda.v20.pricing.ClientPrice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
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
import java.time.LocalTime;
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
            //instrumentDao.save(new Instrument(String.valueOf(clientPrice.getInstrument())));

            Optional<Instrument> optionalInstrument = Optional.ofNullable(new Instrument(String.valueOf(clientPrice.getInstrument())));

            Instrument instrumentToSave = optionalInstrument.get();
            //instrumentDao.save(instrumentToSave);

            AskPriceBucket askPriceBucket = new AskPriceBucket(Double.valueOf(String.valueOf(clientPrice.getAsks().get(0).getPrice())), Long.valueOf(String.valueOf(clientPrice.getAsks().get(0).getLiquidity())));
            askPriceBucketDao.save(askPriceBucket);

            BidPriceBucket bidPriceBucket = new BidPriceBucket(Double.valueOf(String.valueOf(clientPrice.getBids().get(0).getPrice())), Long.valueOf(String.valueOf(clientPrice.getBids().get(0).getLiquidity())));
            bidPriceBucketDao.save(bidPriceBucket);

            Quotation quotation = new Quotation(LocalDateTime.now(), instrumentDao.findById(instrumentToSave.getId()), bidPriceBucket, askPriceBucket);
            quotationDao.save(quotation);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
