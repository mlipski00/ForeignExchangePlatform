package pl.forex.trading_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.domain.user.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findAllByReciverAndIsReadIsFalse(User reciver);
    public List<Message> findAllByReciver(User reciver);
    public List<Message> findAllBySender(User sender);
}
