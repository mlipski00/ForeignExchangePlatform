package pl.forex.trading_platform.service;

import pl.forex.trading_platform.domain.settings.PlatformSettings;

public interface LoadPlatformSettings {

    int loadDecisionTime();

    public PlatformSettings loadAllSettings();

    public Long loadMinimumAmount();

    public Long loadMaximumAmount();

    public Long loadInitialBalnce();
}
