package pl.forex.trading_platform.DAO_legacy;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.Instrument;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
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

    public Instrument findByDescription(String instrumentName) {
        TypedQuery<Instrument> query = entityManager.createQuery("Select i From Instrument i", Instrument.class);
        List<Instrument> instrumentList = query.getResultList();
        for (Instrument instrument : instrumentList) {
            if (instrument.getDescription().equals(instrumentName)) {
                return instrument;
            }
        }
        return null;
    }

    public void update(Instrument entity) {
        entityManager.merge(entity);
    }

    public void delete(Instrument entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));

    }
}
