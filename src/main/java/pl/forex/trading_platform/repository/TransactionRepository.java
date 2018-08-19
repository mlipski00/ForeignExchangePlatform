package pl.forex.trading_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.forex.trading_platform.domain.transactions.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public List<Transaction> findFirst5ByOrderByIdDesc();
}
