package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.forex.trading_platform.domain.transactions.BuySell;
import pl.forex.trading_platform.domain.transactions.ExecutionFailReason;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.domain.user.User;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.service.UserService;

@RestController
@RequestMapping("/transaction")
public class TradeRestController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/buy", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public Transaction buyTrade(@RequestBody Transaction transaction){
        User loggedUser = userService.getLoggedUser();
        System.out.println("buy rest controller procesing");
        System.out.println(transaction.toString());
        transaction.setBuySell(BuySell.BUY);
        if (loggedUser.getBalance() - loggedUser.getBlockedAmount() - transaction.getAmount() >= 0 ) {
            transaction.setExecuted(true);
            transaction.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
            loggedUser.setBlockedAmount(transaction.getAmount());
        } else {
            transaction.setExecuted(false);
            transaction.setClosed(true);
            transaction.setExecutionFailReason(ExecutionFailReason.NOT_ENOUGH_BALANCE.getReason());
            transaction.setProfit(0);
        }
        transactionRepository.save(transaction);
        return transaction;
    }

    @RequestMapping(value = "/sell", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public Transaction sellTrade(@RequestBody Transaction transaction){
        System.out.println("sell rest controller procesing");
        System.out.println(transaction.toString());
        transaction.setBuySell(BuySell.SELL);
        transaction.setExecuted(true);
//        transaction.setExecutionFailReason(ExecutionFailReason.NOT_ENOUGH_BALANCE.getReason());
        transaction.setExecutionFailReason(ExecutionFailReason.STATUS_OK.getReason());
        transactionRepository.save(transaction);
        return transaction;
    }
}
