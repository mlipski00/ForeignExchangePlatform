package pl.forex.trading_platform.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.forex.trading_platform.service.TransactionService;
import pl.forex.trading_platform.service.UserService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AccountDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    final static Logger logger = Logger.getLogger(AccountDetailsController.class);

    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
        model.addAttribute("allProfit", transactionService.getAllProfit());
        model.addAttribute("countOpenTrades", transactionService.countOpenTrades());
        model.addAttribute("countClosedTrades", transactionService.countClosedTrades());
        model.addAttribute("countProfitableTrades", transactionService.countProfitableTrades());
        model.addAttribute("countLosingTrades", transactionService.countLosingTrades());
    }

    @RequestMapping(value = "/accountDetails", method = RequestMethod.GET)
    public String getAccountDetailsPage() {
        logger.debug("User: " + userService.getLoggedUser());
        return "accountDetails";
    }
}