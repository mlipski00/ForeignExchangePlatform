package pl.forex.trading_platform.service;

import com.oanda.v20.pricing.ClientPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.repository.InstrumentRepository;
import pl.forex.trading_platform.repository.QuotationRepository;

import java.util.Optional;

@Service
public class SaveQuotationImpl implements SaveQuotation {


    private InstrumentRepository instrumentRepository;


    private QuotationRepository quotationRepository;
    @Autowired
    public void setInstrumentRepository(InstrumentRepository instrumentRepository) {
        this.instrumentRepository = instrumentRepository;
    }
    @Autowired
    public void setQuotationRepository(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    @Override
    public void saveQuotation(ClientPrice clientPrice) {
        try {
            Optional<Instrument> optionalInstrument = instrumentRepository.findByDescription(clientPrice.getInstrument().toString());

            Instrument instrumentToSave = optionalInstrument.get();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
