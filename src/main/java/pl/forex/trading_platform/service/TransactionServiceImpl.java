package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.DAO_legacy.QuotationDao;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.domain.transactions.BuySell;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.repository.UserRepository;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuotationDao quotationDao;

    @Autowired
    private UserService userService;

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
            transaction.setProfit((transaction.getAmount()*transaction.getClosePrice())-(transaction.getAmount()*transaction.getPrice()));
        }else {
            transaction.setClosePrice(PnLQoutetation.getAskPriceBucket().getPrice());
            transaction.setProfit((transaction.getAmount()*transaction.getPrice())-(transaction.getAmount()*transaction.getClosePrice()));
        }
        transaction.setCloseDateTime(new Date());
        User user = userService.getLoggedUser();
        user.setBalance(updateBalance(transaction));
        user.setBlockedAmount(user.getBlockedAmount()-transaction.getAmount()*transaction.getPrice());
        userRepository.save(user);
        transactionRepository.save(transaction);
    }

    @Override
    public double getAllProfit() {
       return transactionRepository.findAllClosed().stream().map(Transaction::getProfit).reduce(0.0, Double::sum);
    }

    @Override
    public double updateBalance(Transaction transaction) {
        User user = userService.getLoggedUser();
        return user.getBalance()+transaction.getProfit();
    }
}
