package pl.forex.trading_platform.DAO_legacy;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.Quotation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
@PropertySource("classpath:oandaApi.properties")
public class QuotationDao {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${oanda.instrumentsList}")
    private String[] instrumentsList;

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

    public List<Quotation> loadLast() {
        Query query = entityManager.createQuery("SELECT q FROM Quotation q ORDER BY q.id DESC");
        query.setMaxResults(instrumentsList.length);
        return (List<Quotation>) query.getResultList();

    }
}
