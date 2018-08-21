package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.repository.TransactionRepository;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("/opentransactions")
    public String openTransactions(Model model) {
        model.addAttribute("openTransactions", transactionRepository.findFirst5NonClosedDesc());
        return "openTransactions";
    }
}
