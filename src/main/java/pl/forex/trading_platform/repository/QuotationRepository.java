package pl.forex.trading_platform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.domain.Quotation;


public interface QuotationRepository extends CrudRepository<Quotation, Long> {
}
