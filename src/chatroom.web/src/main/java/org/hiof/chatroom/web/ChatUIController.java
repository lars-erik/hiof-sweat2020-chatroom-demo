package org.hiof.chatroom.web;

import org.hiof.chatroom.commands.SendMessageCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatUIController {
    @GetMapping("/")
    public String chatUI(Model model) {
        model.addAttribute("message", "Hello world, dude!!");
        return "chatui";
    }

}
