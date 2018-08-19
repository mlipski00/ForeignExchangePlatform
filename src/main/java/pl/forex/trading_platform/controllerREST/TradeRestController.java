package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.forex.trading_platform.domain.transactions.BuySell;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.repository.TransactionRepository;

@RestController
@RequestMapping("/transaction")
public class TradeRestController {

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping(value = "/buy", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public void addBook(@RequestBody Transaction transaction){
        System.out.println("buy rest controller procesing");
        System.out.println(transaction.toString());
        transaction.setBuySell(BuySell.BUY);
        transactionRepository.save(transaction);
    }
}
