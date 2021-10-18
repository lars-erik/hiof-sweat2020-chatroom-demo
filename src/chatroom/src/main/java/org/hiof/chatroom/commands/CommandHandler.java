package org.hiof.chatroom.commands;

public interface CommandHandler {
    void execute(Command command) throws Exception;
}
