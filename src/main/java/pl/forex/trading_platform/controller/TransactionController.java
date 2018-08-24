package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.service.TransactionService;
import pl.forex.trading_platform.service.UserService;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
    }

    @RequestMapping("/opentransactions")
    public String openTransactions(Model model) {
        model.addAttribute("openTransactions", transactionRepository.findAllNonClosed(userService.getLoggedUser().getId()));
        return "openTransactions";
    }

    @RequestMapping("/closedtransactions")
    public String closedTransactions(Model model) {
        model.addAttribute("closedTransactions", transactionRepository.findAllClosed(userService.getLoggedUser().getId()));
        model.addAttribute("allProfit", transactionService.getAllProfit());
        return "closedTransactions";
    }
}
