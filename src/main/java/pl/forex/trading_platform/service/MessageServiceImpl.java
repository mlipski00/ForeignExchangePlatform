package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<User> getAllRecivers() {
        return userRepository.findAll().stream().filter(user -> !user.equals(userService.getLoggedUser())).collect(Collectors.toList());
    }
}
