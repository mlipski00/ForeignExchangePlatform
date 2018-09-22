package pl.forex.trading_platform.controllerREST;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.service.MessageService;
import pl.forex.trading_platform.service.UserService;

import java.util.List;

@RestController
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    final static Logger logger =Logger.getLogger(MessageRestController.class);

    @RequestMapping("/checkNewMessages")
    public List<Message> checkNewMessages() {
        logger.debug("User: " + userService.getLoggedUser());
        return messageService.GetAllUnreadMessagesByLoggedUser();
    }
}
