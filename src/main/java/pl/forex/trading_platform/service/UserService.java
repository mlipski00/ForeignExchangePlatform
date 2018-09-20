package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.user.User;

import java.util.List;

public interface UserService {

    boolean validUserEmail(String email);

    User getLoggedUser();

    List<User> userRankingList();

    List<User> getAllUsers();

    boolean isLoggedUserAdmin();

    void updateUserBalance(Long usersToUpdateId, double newBalanceAmount);

}
