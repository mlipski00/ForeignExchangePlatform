package pl.forex.trading_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.MessageRepository;
import pl.forex.trading_platform.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<User> getAllRecivers() {
        return userRepository.findAll().stream().filter(user -> !user.equals(userService.getLoggedUser())).collect(Collectors.toList());
    }

    @Override
    public void saveMessage(Message message) {
        message.setSender(userService.getLoggedUser());
        messageRepository.save(message);
    }

    @Override
    public int numberOfUnreadMessages() {
        return messageRepository.findAllByReciverAndIsReadIsFalse(userService.getLoggedUser()).size();
    }

    @Override
    public List<Message> getAllLoggedUserMessages() {
        return messageRepository.findAllByReciver(userService.getLoggedUser());
    }

    @Override
    public Message getSingleMessage(Long id) {
        return messageRepository.getOne(id);
    }

    @Override
    public void setMessageAsRead(Long id) {
        Message message = messageRepository.getOne(id);
        message.setRead(true);
        messageRepository.save(message);
    }

    @Override
    public List<Message> getAllLoggedUserSendMessages() {
        return messageRepository.findAllBySender(userService.getLoggedUser());
    }

    @Override
    public List<Message> GetAllUnreadMessagesByLoggedUser() {
        return messageRepository.findAllByReciverAndIsReadIsFalse(userService.getLoggedUser());
    }

    @Override
    public void notifyUserWithNewBalaceByMessage(Long userId, double newBalance) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(messageReciver -> {
            Message message = new Message();
            message.setSender(userService.getLoggedUser());
            message.setReciver(messageReciver);
            message.setText("Hi " + messageReciver.getUsername() + ". Your balance has been updated by Admin. Current amount: " + newBalance);
            saveMessage(message);
        });
    }
}
