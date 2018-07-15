package pl.forex.trading_platform.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.forex.trading_platform.domain.PlatformSettings;
import pl.forex.trading_platform.repository.PlatformSettingsRepository;

import java.util.List;

@Service
@Getter
@Setter
@Transactional
public class LoadPlatformSettingsImpl implements LoadPlatformSettings {

    @Autowired
    private PlatformSettingsRepository platformSettingsRepository;

    @Override
    public int loadDecisionTime() {
        List<PlatformSettings> platformSettingsList = (List<PlatformSettings>) platformSettingsRepository.findAll();
        return platformSettingsList.get(platformSettingsList.size()-1).getDecisionTime();
    }
}
