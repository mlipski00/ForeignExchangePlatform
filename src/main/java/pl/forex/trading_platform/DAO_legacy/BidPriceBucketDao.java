package pl.forex.trading_platform.DAO_legacy;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.BidPriceBucket;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Transactional
public class BidPriceBucketDao {

    @PersistenceContext
    EntityManager entityManager;

    public void save(BidPriceBucket bidPriceBucket) {
        entityManager.persist(bidPriceBucket);
    }

    public BidPriceBucket findById(long id) {
        return entityManager.find(BidPriceBucket.class, id);
    }

    public void update(BidPriceBucket entity) {
        entityManager.merge(entity);
    }

    public void delete(BidPriceBucket entity) {
        ;
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));

    }
}
