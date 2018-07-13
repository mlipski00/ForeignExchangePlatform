package pl.forex.trading_platform.repository;

import org.springframework.data.repository.CrudRepository;
import pl.forex.trading_platform.domain.BidPriceBucket;

public interface BidPriceBucketRepository extends CrudRepository<BidPriceBucket, Long> {
}
