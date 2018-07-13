package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.service.LoadQuotations;

@Controller
public class QuotationsController {

    @Autowired
    LoadQuotations loadQuotations;

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("quotations", loadQuotations.loadAllQuotations());
        model.addAttribute("instruments", loadQuotations.loadAllInstruments());
        return "index";
    }
}
