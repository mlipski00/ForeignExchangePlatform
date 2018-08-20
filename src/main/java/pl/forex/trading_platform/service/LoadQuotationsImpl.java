package pl.forex.trading_platform.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.DAO_legacy.QuotationDao;
import pl.forex.trading_platform.domain.AskPriceBucket;
import pl.forex.trading_platform.domain.BidPriceBucket;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.repository.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
@Transactional
public class LoadQuotationsImpl implements LoadQuotations {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private BidPriceBucketRepository bidPriceBucketRepository;

    @Autowired
    private AskPriceBucketRepository askPriceBucketRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private QuotationDao quotationDao;

    @Override
    public List<Quotation> loadAllQuotations() {
        return (List<Quotation>) quotationRepository.findAll();
    }

    @Override
    public List<AskPriceBucket> loadAllAskPrices() {
        return (List<AskPriceBucket>) askPriceBucketRepository.findAll();
    }

    @Override
    public List<BidPriceBucket> loadAllBidPrices() {
        return (List<BidPriceBucket>) bidPriceBucketRepository.findAll();
    }

    @Override
    public List<Instrument> loadAllInstruments() {
        return (List<Instrument>) instrumentRepository.findAll();
    }

    @Override
    public List<Quotation> loadLastQuotations() {
        return quotationDao.loadLast().stream()
                .sorted(Comparator.comparing(q -> q.getInstrument().getDescription()))
                .collect(Collectors.toList());
    }
}
