package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.domain.nbp.TableA;
import pl.forex.trading_platform.domain.transactions.Transaction;
import pl.forex.trading_platform.repository.TransactionRepository;
import pl.forex.trading_platform.service.*;

import java.util.List;

@Controller
@PropertySource("classpath:platformSettings.properties")
public class MainPageController {

    @Autowired
    LoadQuotations loadQuotations;

    @Autowired
    private LoadPlatformSettings loadPlatformSettings;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    NbpRates nbpRates;

    @Value("${platformSettings.nbpTableA}")
    private String nbpTableAurl;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoggerService loggerService;

    @RequestMapping({"/index"})
    public String getIndexPage(Model model) {
        List<Quotation> quotations = loadQuotations.loadAllQuotations();
        List<Instrument> instruments = loadQuotations.loadAllInstruments();
        model.addAttribute("quotations", quotations.subList(Math.max(quotations.size() - instruments.size() * 3, 0), quotations.size()));
        model.addAttribute("instruments", instruments);
        return "index";
    }

    @RequestMapping({"", "/", "/websocket"})
    public String webSocketPage(Model model) {
        loggerService.logToFile("Info log message. Method name: " + new Object(){}.getClass().getEnclosingMethod().getName());
        loggerService.sendLogFileToEmail();
        model.addAttribute("decisionTime", loadPlatformSettings.loadDecisionTime());
        model.addAttribute("minimumTradeAmount", loadPlatformSettings.loadMinimumAmount());
        model.addAttribute("maximumTradeAmount", loadPlatformSettings.loadMaximumAmount());
        TableA[] tableAarray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        model.addAttribute("nbpTableA", tableAarray[0]);
        model.addAttribute("openTransactions", transactionRepository.findFirst5NonClosedDesc(userService.getLoggedUser().getId()));
        model.addAttribute("closedTransactions", transactionRepository.findFirst5ClosedDesc(userService.getLoggedUser().getId()));
        model.addAttribute("loggedUser", userService.getLoggedUser().getUsername());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
        model.addAttribute("isUserAdmin", userService.isLoggedUserAdmin());
        return "websocket";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/transaction/{id}/close")
    public String closeTransaction(@PathVariable String id){
        loggerService.logToFile("Info log message. Method name: " + new Object(){}.getClass().getEnclosingMethod().getName());
        Transaction transaction = transactionRepository.getOne(Long.valueOf(id));
        transactionService.closeTransaction(transaction);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginpage(){
        loggerService.logToFile("Info log message. Method name: " + new Object(){}.getClass().getEnclosingMethod().getName());
        return "login";
    }


}
