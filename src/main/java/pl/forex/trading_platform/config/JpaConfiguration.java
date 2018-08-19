package pl.forex.trading_platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.forex.trading_platform.DAO_legacy.InstrumentDao;
import pl.forex.trading_platform.repository.InstrumentRepository;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {InstrumentRepository.class, InstrumentDao.class})
public class JpaConfiguration {
}
