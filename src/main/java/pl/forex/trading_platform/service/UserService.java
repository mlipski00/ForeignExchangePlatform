package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.user.User;

public interface UserService {

    public boolean validUserEmail(String email);

    User getLoggedUser();
}
