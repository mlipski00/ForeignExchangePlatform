package pl.forex.trading_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.service.MessageService;
import pl.forex.trading_platform.service.UserService;

import javax.validation.Valid;

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

    @RequestMapping(value = "/newMessage", method = RequestMethod.POST)
    public String processMessageForm(@Valid Message message, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", message);
            model.addAttribute("recivers", messageService.getAllRecivers());
            return "messageForm";
        }
        messageService.saveMessage(message);
        model.addAttribute("recivers", messageService.getAllRecivers());
        model.addAttribute("sendingMessageResult", 1);
        return "messageForm";
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String getInboxPage(Model model) {
        model.addAttribute("title", "Inbox");
        model.addAttribute("messages", messageService.getAllLoggedUserMessages());
        return "messageList";
    }

    @RequestMapping(value = "/outbox", method = RequestMethod.GET)
    public String getOutboxage(Model model) {
        model.addAttribute("title", "Outbox");
        model.addAttribute("messages", messageService.getAllLoggedUserSendMessages());
        return "messageList";
    }

    @RequestMapping(value = "/messages/Inbox/{id}", method = RequestMethod.GET)
    public String getSingleInboxMessageDetails(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageService.getSingleMessage(id));
        messageService.setMessageAsRead(id);
        return "messageSinglePage";
    }

    @RequestMapping(value = "/messages/Outbox/{id}", method = RequestMethod.GET)
    public String getSingleOutboxMessageDetails(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageService.getSingleMessage(id));
        return "messageSinglePage";
    }
}
