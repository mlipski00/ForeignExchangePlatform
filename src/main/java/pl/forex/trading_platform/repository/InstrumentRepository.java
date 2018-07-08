package pl.forex.trading_platform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Instrument;

import java.util.Optional;


public interface InstrumentRepository extends CrudRepository<Instrument, Long> {

    Optional<Instrument> findByDescription(String description);

}
