package pl.forex.trading_platform.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.forex.trading_platform.domain.user.Message;
import pl.forex.trading_platform.service.MessageService;

import java.util.List;

@RestController
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/checkNewMessages")
    public List<Message> checkNewMessages() {
        return messageService.GetAllUnreadMessagesByLoggedUser();
    }
}
