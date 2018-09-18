package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.settings.PlatformSettings;

public interface LoadPlatformSettings {

    int loadDecisionTime();

    PlatformSettings loadAllSettings();

    Long loadMinimumAmount();

    Long loadMaximumAmount();

    Long loadInitialBalnce();
}
