package pl.forex.trading_platform.repository;

import org.springframework.data.repository.CrudRepository;
import pl.forex.trading_platform.domain.PlatformSettings;

public interface PlatformSettingsRepository extends CrudRepository<PlatformSettings, Long> {
}
