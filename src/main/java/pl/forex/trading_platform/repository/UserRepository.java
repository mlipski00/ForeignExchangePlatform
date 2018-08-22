package pl.forex.trading_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.forex.trading_platform.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
