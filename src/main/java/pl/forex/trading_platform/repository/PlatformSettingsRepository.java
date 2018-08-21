package pl.forex.trading_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.forex.trading_platform.domain.settings.PlatformSettings;

public interface PlatformSettingsRepository extends JpaRepository<PlatformSettings, Long> {

}
