package pl.forex.trading_platform.repository;

import org.springframework.data.repository.CrudRepository;
import pl.forex.trading_platform.domain.AskPriceBucket;

public interface AskPriceBucketRepository extends CrudRepository<AskPriceBucket, Long> {
}
