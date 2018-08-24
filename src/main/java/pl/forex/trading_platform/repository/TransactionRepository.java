package pl.forex.trading_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.forex.trading_platform.domain.transactions.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findAllByOrderByIdDesc();

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = FALSE AND USER_USER_ID  = ?1 ORDER BY TRANSACTION_ID DESC", nativeQuery = true)
    public List<Transaction> findAllNonClosed(Long user_id);

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = TRUE AND USER_USER_ID  = ?1 ORDER BY TRANSACTION_ID DESC", nativeQuery = true)
    public List<Transaction> findAllClosed(Long user_id);

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = FALSE AND USER_USER_ID  = ?1 ORDER BY TRANSACTION_ID DESC LIMIT 5", nativeQuery = true)
    public List<Transaction> findFirst5NonClosedDesc(Long user_id);

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = TRUE AND IS_EXECUTED = TRUE AND USER_USER_ID  = ?1 ORDER BY TRANSACTION_ID DESC LIMIT 5", nativeQuery = true)
    public List<Transaction> findFirst5ClosedDesc(Long user_id);
}
