package pl.forex.trading_platform.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.Instrument;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@Qualifier("instrumentDAO")
@Transactional
public class InstrumentDao {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Instrument instrument) {
        entityManager.persist(instrument);
    }

    public Instrument findById(long id) {
        return entityManager.find(Instrument.class, id);
    }

    public void update(Instrument entity) {
        entityManager.merge(entity);
    }
    public void delete(Instrument entity) { ;
        entityManager.remove(entityManager.contains(entity) ?
                entity : entityManager.merge(entity));

    }
}
