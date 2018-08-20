package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.transactions.Transaction;

public interface TransactionService {

    public void closeTransaction(Transaction transaction);
}
