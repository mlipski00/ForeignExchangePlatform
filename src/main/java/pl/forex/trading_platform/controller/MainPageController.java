package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import pl.forex.trading_platform.service.LoadPlatformSettings;
import pl.forex.trading_platform.service.LoadQuotations;
import pl.forex.trading_platform.service.NbpRates;
import pl.forex.trading_platform.service.TransactionService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
        model.addAttribute("decisionTime", loadPlatformSettings.loadDecisionTime());
        TableA[] tableAarray = nbpRates.getTableAQuotesArray(nbpTableAurl);
        model.addAttribute("nbpTableA", tableAarray[0]);
        model.addAttribute("openTransactions", transactionRepository.findFirst5NonClosedDesc());
        model.addAttribute("closedTransactions", transactionRepository.findFirst5ClosedDesc());
        return "websocket";
    }

    @GetMapping("/transaction/{id}/close")
    public String closeTransaction(@PathVariable String id){
        Transaction transaction = transactionRepository.getOne(Long.valueOf(id));
        transactionService.closeTransaction(transaction);
        return "redirect:/";
    }

}
