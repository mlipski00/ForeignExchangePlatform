package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.service.TransactionService;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/opentransactions")
    public String openTransactions(Model model) {
        model.addAttribute("openTransactions", transactionRepository.findAllNonClosed());
        return "openTransactions";
    }

    @RequestMapping("/closedtransactions")
    public String closedTransactions(Model model) {
        model.addAttribute("closedTransactions", transactionRepository.findAllClosed());
        model.addAttribute("allProfit", transactionService.getAllProfit());
        return "closedTransactions";
    }
}
