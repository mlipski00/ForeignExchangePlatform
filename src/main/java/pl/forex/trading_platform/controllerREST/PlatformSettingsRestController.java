package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.service.LoadPlatformSettings;

@RestController
@RequestMapping("/platformsettings")
public class PlatformSettingsRestController {

    @Autowired
    private LoadPlatformSettings loadPlatformSettings;

    @RequestMapping("/decisiontime")
    public int getDecisionTime() {
        return loadPlatformSettings.loadDecisionTime();
    }
}
