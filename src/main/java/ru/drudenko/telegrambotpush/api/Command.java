package ru.drudenko.telegrambotpush.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    SendMessage process(Update update);

    boolean isSupportCommand(Update update);

    String getCommand();
}
