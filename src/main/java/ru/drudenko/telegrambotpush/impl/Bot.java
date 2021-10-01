package ru.drudenko.telegrambotpush.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drudenko.telegrambotpush.api.Command;
import ru.drudenko.telegrambotpush.api.SessionService;
import ru.drudenko.telegrambotpush.impl.command.DefaultCommand;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {

    private final List<Command> commands;
    private final SessionService sessionService;
    @Value("${name}")
    private String botUsername;
    @Value("${token}")
    private String botToken;

    public Bot(@Lazy List<Command> commands, SessionService sessionService) {
        this.commands = commands;
        this.sessionService = sessionService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            String nextCommand = sessionService.getNextCommand(update.getMessage().getChatId());
            Command cmd = commands.stream()
                    .filter(command -> command.isSupportCommand(update))
                    .findAny()
                    .orElseGet(() -> {
                        if(nextCommand == null) {
                            return commands
                                    .stream()
                                    .filter(command -> command.getCommand().equals(DefaultCommand.COMMAND))
                                    .findAny().orElseThrow(IllegalArgumentException::new);
                        } else{
                            sessionService.removeNextCommand(update.getMessage().getChatId());
                            return commands
                                    .stream()
                                    .filter(command -> command.getCommand().equals(nextCommand))
                                    .findAny().orElseThrow(IllegalArgumentException::new);
                        }
                    });

            SendMessage msg = cmd.process(update);
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Геттеры, которые необходимы для наследования от TelegramLongPollingBot
    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

}
