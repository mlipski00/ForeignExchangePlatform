package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.User;

import java.util.List;

public interface UserService {

    public boolean validUserEmail(String email);

    User getLoggedUser();

    Transaction processTtransaction(Transaction transaction, User loggedUser);

    List<User> userRankingList();

    public boolean isLoggedUserAdmin();
}
