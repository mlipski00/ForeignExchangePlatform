package pl.forex.trading_platform.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.forex.trading_platform.domain.settings.PlatformSettings;
import pl.forex.trading_platform.repository.PlatformSettingsRepository;
import pl.forex.trading_platform.service.UserService;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformSettingsRepository platformSettingsRepository;

    final static Logger logger = Logger.getLogger(AdminController.class);

    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
        model.addAttribute("isUserAdmin", userService.isLoggedUserAdmin());
    }
    @RequestMapping(value = "/adminpanel", method = RequestMethod.GET)
    public String getUpdateSettingsForm(Model model) {
        model.addAttribute("platformSettings", platformSettingsRepository.getOne(1l));
    return "adminpanel";
    }

    @RequestMapping(value = "/adminpanel", method = RequestMethod.POST)
    public String processUpdateSettings(@Valid PlatformSettings platformSettings, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("platformSettings", platformSettings);
            model.addAttribute("updateSettingsResult", 1);
            logger.error("@RequestMapping(value = \"/adminpanel\", method = RequestMethod.POST) with error result: " + result.getAllErrors().toString() + " called by user: " + userService.getLoggedUser());
            return "adminpanel";
        }
        if (platformSettings.getMaximumTradeAmount() < platformSettings.getMinimumTradeAmount()){
            model.addAttribute("wrongMaximumTradeAmount", true);
            model.addAttribute("platformSettings", platformSettings);
            model.addAttribute("updateSettingsResult", 1);
            logger.error("@RequestMapping(value = \"/adminpanel\", method = RequestMethod.POST) with error result: maxAmount ("+platformSettings.getMaximumTradeAmount()+") < minAmount ("+platformSettings.getMinimumTradeAmount()+") called by user: " + userService.getLoggedUser());
            return "adminpanel";
        }
        model.addAttribute("updateSettingsResult", 2);
        PlatformSettings platformSettingsUpdated = platformSettingsRepository.getOne(1l);
        platformSettingsUpdated.setDecisionTime(platformSettings.getDecisionTime());
        platformSettingsUpdated.setMinimumTradeAmount(platformSettings.getMinimumTradeAmount());
        platformSettingsUpdated.setMaximumTradeAmount(platformSettings.getMaximumTradeAmount());
        platformSettingsUpdated.setInitialBalance(platformSettings.getInitialBalance());
        platformSettingsRepository.save(platformSettingsUpdated);
        logger.debug("@RequestMapping(value = \"/adminpanel\", method = RequestMethod.POST) called by user: " + userService.getLoggedUser());
        return "adminpanel";
    }
}
