package ru.drudenko.telegrambotpush.api;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionService {
    private final Map<Long, String> commandsSession = new HashMap<>();

    public String getNextCommand(Long chatId) {
        return commandsSession.get(chatId);
    }

    public void putNextCommand(Long chatId, String cmd) {
        commandsSession.put(chatId, cmd);
    }

    public void removeNextCommand(Long chatId){
        commandsSession.remove(chatId);
    }
}
