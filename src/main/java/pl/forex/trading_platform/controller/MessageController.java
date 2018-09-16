package pl.forex.trading_platform.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ROLE_USER')")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    final static Logger logger = Logger.getLogger(MessageController.class);
    @ModelAttribute
    public void addLoggedUserAttributes(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
        model.addAttribute("loggedUserBalance", userService.getLoggedUser().getBalance());
        model.addAttribute("loggedUserBlockedAmount", userService.getLoggedUser().getBlockedAmount());
    }

    @RequestMapping(value = "/newMessage", method = RequestMethod.GET)
    public String getMessageForm(Model model) {
        model.addAttribute("message", new Message());
        model.addAttribute("recivers", messageService.getAllRecivers());
        logger.debug("@RequestMapping(value = \"/newMessage\", method = RequestMethod.GET) called by user: " + userService.getLoggedUser());
        return "messageForm";
    }

    @RequestMapping(value = "/newMessage", method = RequestMethod.POST)
    public String processMessageForm(@Valid Message message, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", message);
            model.addAttribute("recivers", messageService.getAllRecivers());
            logger.error("@RequestMapping(value = \"/newMessage\", method = RequestMethod.POST) with error result: " + result.getAllErrors().toString() + " called by user: " + userService.getLoggedUser());
            return "messageForm";
        }
        messageService.saveMessage(message);
        model.addAttribute("recivers", messageService.getAllRecivers());
        model.addAttribute("sendingMessageResult", 1);
        logger.debug("@RequestMapping(value = \"/newMessage\", method = RequestMethod.POST) called by user: " + userService.getLoggedUser());
        return "messageForm";
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String getInboxPage(Model model) {
        model.addAttribute("title", "Inbox");
        model.addAttribute("messages", messageService.getAllLoggedUserMessages());
        logger.debug("@RequestMapping(value = \"/inbox\", method = RequestMethod.GET) called by user: " + userService.getLoggedUser());
        return "messageList";
    }

    @RequestMapping(value = "/outbox", method = RequestMethod.GET)
    public String getOutboxage(Model model) {
        model.addAttribute("title", "Outbox");
        model.addAttribute("messages", messageService.getAllLoggedUserSendMessages());
        logger.debug("@RequestMapping(value = \"/outbox\", method = RequestMethod.GET) called by user: " + userService.getLoggedUser());
        return "messageList";
    }

    @RequestMapping(value = "/messages/Inbox/{id}", method = RequestMethod.GET)
    public String getSingleInboxMessageDetails(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageService.getSingleMessage(id));
        messageService.setMessageAsRead(id);
        logger.debug("@RequestMapping(value = \"/messages/Inbox/{id}\", method = RequestMethod.GET) called by user: " + userService.getLoggedUser());
        return "messageSinglePage";
    }

    @RequestMapping(value = "/messages/Outbox/{id}", method = RequestMethod.GET)
    public String getSingleOutboxMessageDetails(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageService.getSingleMessage(id));
        logger.debug("@RequestMapping(value = \"/messages/Outbox/{id}\", method = RequestMethod.GET) called by user: " + userService.getLoggedUser());
        return "messageSinglePage";
    }
}
