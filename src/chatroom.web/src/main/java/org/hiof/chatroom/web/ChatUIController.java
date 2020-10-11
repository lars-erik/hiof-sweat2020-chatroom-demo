package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.persistence.ChatMessageRepository;
import org.hiof.chatroom.persistence.PersistenceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatUIController {
    private NotificationDispatcher dispatcher;

    public ChatUIController(NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @GetMapping("/")
    public String chatUI(Model model) throws Exception {
        ChatMessageRepository chatMessageRepository = PersistenceFactory.Instance.createChatMessageRepository(
                PersistenceFactory.Instance.createUnitOfWork()
        );
        List<String> lastMessages = chatMessageRepository.getLastMessages();

        model.addAttribute("log", lastMessages);
        return "chatui";
    }

    @MessageMapping("/start")
    public void start(StompHeaderAccessor accessor) {
        dispatcher.add(accessor.getSessionId());
    }

    @MessageMapping("/stop")
    public void stop(StompHeaderAccessor accessor) {
        dispatcher.remove(accessor.getSessionId());
    }

    @RequestMapping(value = "/postmessage", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void postMessage(
            @RequestParam(name="username") String userName,
            @RequestParam(name="message") String message
    ) throws Exception {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = userName;
        cmd.message = message;
        cmd.execute();
    }

}
