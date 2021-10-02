package ru.drudenko.telegrambotpush.impl.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.drudenko.telegrambotpush.api.Command;
import ru.drudenko.telegrambotpush.api.NotificationService;
import ru.drudenko.telegrambotpush.model.Notification;

import java.util.Date;

@Service
public class CreateAddPeriodCommand implements Command {
    public static final String COMMAND = "create_notification_add_period";
    private final NotificationService notificationService;

    public CreateAddPeriodCommand(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @Override
    public SendMessage process(Update message) {

        Notification temp = notificationService.getTempMessage(message.getMessage().getChatId());
        temp.setPeriod(message.getMessage().getText());
        temp.setOffset(new Date(message.getMessage().getDate()).getTimezoneOffset());
        notificationService.add(message.getMessage().getChatId(), temp);
        notificationService.removeTemp(message.getMessage().getChatId());

        return SendMessage.builder().chatId(String.valueOf(message.getMessage().getChatId()))
                .text("Напоминалка заведена, " + temp)
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
