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
    public String chatUI(Model model) {
        model.addAttribute("log", queryDispatcher.<List<String>>query(new NewMessagesQuery(20)));
        return "chatui";
    }

    @RequestMapping(value = "/postmessage", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void postMessage(@RequestBody SendMessageCommand cmd) throws Exception {
        commandDispatcher.execute(cmd);
    }

    @MessageMapping("/start")
    public void start(StompHeaderAccessor accessor) {
        dispatcher.add(accessor.getSessionId());
    }

    @MessageMapping("/stop")
    public void stop(StompHeaderAccessor accessor) {
        dispatcher.remove(accessor.getSessionId());
    }
}
