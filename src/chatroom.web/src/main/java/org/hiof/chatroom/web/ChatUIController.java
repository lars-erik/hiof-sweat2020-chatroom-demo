package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatUIController {
    @GetMapping("/")
    public String chatUI(Model model) {
        model.addAttribute("message", "Hello world, dude!!");
        return "chatui";
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
        cmd.execute();
    }
}
