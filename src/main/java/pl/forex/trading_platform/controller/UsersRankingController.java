package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.forex.trading_platform.service.UserService;

@Controller
public class UsersRankingController {

    @Autowired
    private UserService userService;


    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
        model.addAttribute("userRankingList", userService.userRankingList());
    }

    @RequestMapping(value = "/usersRanking", method = RequestMethod.GET)
    public String getAccountDetailsPage() {
        return "usersRanking";
    }
}