package pl.forex.trading_platform.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.forex.trading_platform.domain.Instrument;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InstrumentRepositoryTest {

    @Autowired
    InstrumentRepository instrumentRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescription() throws Exception {
        Optional<Instrument> optionalInstrument = instrumentRepository.findByDescription("EURUSD");
        assertEquals("EURUSD", optionalInstrument.get().getDescription());
    }
}