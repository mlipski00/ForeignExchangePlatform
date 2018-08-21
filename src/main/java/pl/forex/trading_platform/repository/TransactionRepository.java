package pl.forex.trading_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.forex.trading_platform.domain.transactions.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findAllByOrderByIdDesc();

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = FALSE", nativeQuery = true)
    public List<Transaction> findAllNonClosed();

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = TRUE", nativeQuery = true)
    public List<Transaction> findAllClosed();

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = FALSE LIMIT 5", nativeQuery = true)
    public List<Transaction> findFirst5NonClosedDesc();

    @Query(value = "SELECT * FROM TRANSACTION WHERE IS_CLOSED = TRUE LIMIT 5", nativeQuery = true)
    public List<Transaction> findFirst5ClosedDesc();
}
