package pl.forex.trading_platform.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.service.TransactionService;
import pl.forex.trading_platform.service.UserService;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    final static Logger logger = Logger.getLogger(TransactionController.class);

    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
    }

    @RequestMapping("/opentransactions")
    public String openTransactions(Model model) {
        model.addAttribute("openTransactions", transactionRepository.findAllNonClosed(userService.getLoggedUser().getId()));
        logger.debug("User: " + userService.getLoggedUser());
        return "openTransactions";
    }

    @RequestMapping("/closedtransactions")
    public String closedTransactions(Model model) {
        model.addAttribute("closedTransactions", transactionRepository.findAllClosed(userService.getLoggedUser().getId()));
        model.addAttribute("allProfit", transactionService.getAllProfit());
        logger.debug("User: " + userService.getLoggedUser());
        return "closedTransactions";
    }
}
