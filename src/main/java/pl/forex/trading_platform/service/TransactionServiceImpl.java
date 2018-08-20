package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.DAO_legacy.QuotationDao;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.domain.transactions.BuySell;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.repository.TransactionRepository;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private QuotationDao quotationDao;

    @Override
    public void closeTransaction(Transaction transaction) {
        transaction.setClosed(true);
        String PnLConversionPair = transaction.getInstrument().substring(0,3) + "_PLN";
        Quotation PnLQoutetation = quotationDao.loadLast().stream()
                .filter(PnLQoute -> PnLConversionPair.equals(PnLQoute.getInstrument().getDescription()))
                .findAny()
                .orElse(null);
        if (transaction.getBuySell().equals(BuySell.BUY)) {
            transaction.setClosePrice(PnLQoutetation.getBidPriceBucket().getPrice());
        }else {
            transaction.setClosePrice(PnLQoutetation.getAskPriceBucket().getPrice());
        }
        transaction.setCloseDateTime(new Date());
        transactionRepository.save(transaction);
    }
}
