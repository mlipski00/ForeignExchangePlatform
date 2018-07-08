package pl.forex.trading_platform.service;

import com.oanda.v20.pricing.ClientPrice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.repository.InstrumentDao;

import java.util.Optional;

@Component
@Getter
@Setter
public class SaveQuotationImpl implements SaveQuotation {

    @Autowired
    @Qualifier("instrumentDAO")
    private InstrumentDao instrumentDao;

    public SaveQuotationImpl() {
    }

    @Override
    @Transactional
    public void saveQuotation(ClientPrice clientPrice) {
        try {
           // instrumentDao = new InstrumentDao();
            instrumentDao.save(new Instrument("ASDASD"));
            Optional<Instrument> optionalInstrument = Optional.ofNullable(instrumentDao.findById(1));

            Instrument instrumentToSave = optionalInstrument.get();
            instrumentDao.save(instrumentToSave);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
