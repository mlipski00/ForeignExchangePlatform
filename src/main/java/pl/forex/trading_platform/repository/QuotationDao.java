package pl.forex.trading_platform.repository;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.Quotation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class QuotationDao {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Quotation quotation) {
        entityManager.persist(quotation);
    }

    public Quotation findById(long id) {
        return entityManager.find(Quotation.class, id);
    }

    public void update(Quotation entity) {
        entityManager.merge(entity);
    }
    public void delete(Quotation entity) { ;
        entityManager.remove(entityManager.contains(entity) ?
                entity : entityManager.merge(entity));

    }
}
