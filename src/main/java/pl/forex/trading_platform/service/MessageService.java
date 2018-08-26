package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.user.User;

import java.util.List;

public interface MessageService {

    public List<User> getAllRecivers();
}
