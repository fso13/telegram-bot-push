package ru.drudenko.telegrambotpush.impl.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.drudenko.telegrambotpush.api.Command;
import ru.drudenko.telegrambotpush.api.NotificationService;
import ru.drudenko.telegrambotpush.api.SessionService;
import ru.drudenko.telegrambotpush.model.Notification;

@Service
public class CreateAddTitleCommand implements Command {
    public static final String COMMAND = "create_notification_add_title";
    private final NotificationService notificationService;
    private final SessionService sessionService;

    public CreateAddTitleCommand(NotificationService notificationService, SessionService sessionService) {
        this.notificationService = notificationService;
        this.sessionService = sessionService;
    }

    @Override
    public SendMessage process(Update message) {
        sessionService.putNextCommand(message.getMessage().getChatId(), CreateAddTimeCommand.COMMAND);

        Notification temp = new Notification();
        temp.setTitle(message.getMessage().getText());

        notificationService.addTemp(message.getMessage().getChatId(), temp);
        return SendMessage.builder().chatId(String.valueOf(message.getMessage().getChatId()))
                .text("Введите время напоминания(например 11:45)")
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
