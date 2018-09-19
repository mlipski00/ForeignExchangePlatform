package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.User;

public interface TransactionService {

    void closeTransaction(Transaction transaction);

    double getAllProfit();

    double updateBalance(Transaction transaction);

    long countOpenTrades();

    long countClosedTrades();

    long countProfitableTrades();

    long countLosingTrades();

    Transaction processTtransaction(Transaction transaction, User loggedUser);

}
