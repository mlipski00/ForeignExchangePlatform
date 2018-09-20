package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.domain.user.User;

import java.util.List;

public interface MessageService {

    List<User> getAllRecivers();

    void saveMessage(Message message);

    int numberOfUnreadMessages();

    List<Message> getAllLoggedUserMessages();

    Message getSingleMessage(Long id);

    void setMessageAsRead(Long id);

    List<Message> getAllLoggedUserSendMessages();

    List<Message> GetAllUnreadMessagesByLoggedUser();

    void notifyUserWithNewBalaceByMessage(Long userId, double newBalance);
}
