package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.hiof.chatroom.commands.SendMessageCommandHandler;
import org.hiof.chatroom.queries.NewMessagesQuery;
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
    private QueryDispatcher queryDispatcher;
    private CommandDispatcher commandDispatcher;

    public ChatUIController(NotificationDispatcher dispatcher, QueryDispatcher queryDispatcher, CommandDispatcher commandDispatcher) {
        this.dispatcher = dispatcher;
        this.queryDispatcher = queryDispatcher;
        this.commandDispatcher = commandDispatcher;
    }

    @GetMapping("/")
    public String chatUI(Model model) throws Exception {
        NewMessagesQuery query = new NewMessagesQuery(20);
        List<String> result = queryDispatcher.query(query);
        model.addAttribute("log", result);
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
    ) {
        SendMessageCommand cmd = new SendMessageCommand();
        cmd.user = userName;
        cmd.message = message;
        commandDispatcher.execute(cmd);
    }

}
