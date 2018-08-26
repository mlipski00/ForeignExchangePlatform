package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.service.MessageService;
import pl.forex.trading_platform.service.UserService;

@Controller
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
    }

//    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @RequestMapping(value = "/newMessage", method = RequestMethod.GET)
    public String getMessageForm(Model model) {
        model.addAttribute("message", new Message());
        model.addAttribute("recivers", messageService.getAllRecivers());
        return "messageForm";
    }
}
