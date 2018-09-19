package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.forex.trading_platform.domain.transactions.BuySell;
import pl.forex.trading_platform.domain.transactions.ExecutionFailReason;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.service.EmailService;
import pl.forex.trading_platform.service.TransactionService;
import pl.forex.trading_platform.service.UserService;

@RestController
@RequestMapping("/transaction")
public class TradeRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionService transactionService;

    private final String emailConfirmationSubject = "Forex transaction confirmation";

    @RequestMapping(value = "/buy", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public Transaction buyTrade(@RequestBody Transaction transaction){
        User loggedUser = userService.getLoggedUser();
        System.out.println("buy rest controller procesing");
        System.out.println(transaction.toString());
        transaction.setBuySell(BuySell.BUY);
        emailService.sendEmail(loggedUser.getEmail(), emailConfirmationSubject, transaction.toString());
        return transactionService.processTtransaction(transaction, loggedUser);
    }

    @RequestMapping(value = "/sell", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public Transaction sellTrade(@RequestBody Transaction transaction){
        User loggedUser = userService.getLoggedUser();
        System.out.println("sell rest controller procesing");
        System.out.println(transaction.toString());
        transaction.setBuySell(BuySell.SELL);
        emailService.sendEmail(loggedUser.getEmail(), emailConfirmationSubject, transaction.toString());
        return transactionService.processTtransaction(transaction, loggedUser);
    }
}
