package pl.forex.trading_platform.repository;

import org.springframework.data.repository.CrudRepository;
import pl.forex.trading_platform.domain.nbp.TableA;

public interface NbpRatesRepository extends CrudRepository<TableA, Long> {
}
