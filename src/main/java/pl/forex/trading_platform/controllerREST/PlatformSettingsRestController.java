package pl.forex.trading_platform.controllerREST;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.domain.settings.PlatformSettings;
import pl.forex.trading_platform.service.LoadPlatformSettings;
import pl.forex.trading_platform.service.UserService;

@RestController
@RequestMapping("/platformsettings")
public class PlatformSettingsRestController {

    @Autowired
    private LoadPlatformSettings loadPlatformSettings;

    @Autowired
    private UserService userService;

    final static Logger logger =Logger.getLogger(PlatformSettingsRestController.class);

    @RequestMapping("/decisiontime")
    public int getDecisionTime() {

        logger.debug("User: " + userService.getLoggedUser());
        return loadPlatformSettings.loadDecisionTime();
    }

    @RequestMapping("/allsettings")
    public PlatformSettings getAllSettings() {

        logger.debug("User: " + userService.getLoggedUser());
        return loadPlatformSettings.loadAllSettings();
    }
}