package pl.forex.trading_platform.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.forex.trading_platform.domain.Instrument;
import pl.forex.trading_platform.domain.Quotation;
import pl.forex.trading_platform.service.LoadPlatformSettings;
import pl.forex.trading_platform.service.LoadQuotations;
import pl.forex.trading_platform.service.NbpRates;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    LoadQuotations loadQuotations;

    @Autowired
    private LoadPlatformSettings loadPlatformSettings;

    @Autowired
    NbpRates nbpRates;

    @RequestMapping({"/index"})
    public String getIndexPage(Model model) {
        List<Quotation> quotations = loadQuotations.loadAllQuotations();
        List<Instrument> instruments = loadQuotations.loadAllInstruments();
        model.addAttribute("quotations", quotations.subList(Math.max(quotations.size() - instruments.size() * 3, 0), quotations.size()));
        model.addAttribute("instruments", instruments);
        return "index";
    }

    @RequestMapping({"", "/", "/websocket"})
    public String webSocketPage(HttpServletResponse httpServletResponse, Model model) {
        model.addAttribute("decisionTime", loadPlatformSettings.loadDecisionTime());
        JSONArray nbpJsonArray = null;
        try {
            nbpJsonArray = new JSONArray(nbpRates.getTableAQuotes("http://api.nbp.pl/api/exchangerates/tables/A/"));
            model.addAttribute("nbpTableA", nbpJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "websocket";
    }
}
