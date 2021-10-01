package ru.drudenko.telegrambotpush.impl.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.drudenko.telegrambotpush.api.Command;
import ru.drudenko.telegrambotpush.api.SessionService;

@Service
public class CreateStartCommand implements Command {
    public static final String COMMAND = "Создать напоминалку";
    private final SessionService sessionService;

    public CreateStartCommand(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public SendMessage process(Update message) {
        sessionService.putNextCommand(message.getMessage().getChatId(), CreateAddTitleCommand.COMMAND);

        return SendMessage.builder().chatId(String.valueOf(message.getMessage().getChatId()))
                .text("О чем Вам напомнить?")
                .build()
                ;


    }

    @Override
    public boolean isSupportCommand(Update message) {
        return message.getMessage().getText().equals(COMMAND);
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }
}
