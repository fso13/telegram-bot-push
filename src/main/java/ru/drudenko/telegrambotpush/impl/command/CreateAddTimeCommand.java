package ru.drudenko.telegrambotpush.impl.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.drudenko.telegrambotpush.api.Command;
import ru.drudenko.telegrambotpush.api.NotificationService;
import ru.drudenko.telegrambotpush.api.SessionService;
import ru.drudenko.telegrambotpush.impl.period.ParsePeriodService;
import ru.drudenko.telegrambotpush.model.Notification;

import java.time.LocalTime;

@Service
public class CreateAddTimeCommand implements Command {
    public static final String COMMAND = "create_notification_add_time";
    private final NotificationService notificationService;
    private final SessionService sessionService;
    private final ParsePeriodService parsePeriod;

    public CreateAddTimeCommand(NotificationService notificationService,
                                SessionService sessionService,
                                ParsePeriodService parsePeriod) {
        this.notificationService = notificationService;
        this.sessionService = sessionService;
        this.parsePeriod = parsePeriod;
    }

    @Override
    public SendMessage process(Update message) {
        sessionService.putNextCommand(message.getMessage().getChatId(), CreateAddPeriodCommand.COMMAND);

        Notification temp = notificationService.getTempMessage(message.getMessage().getChatId());
        temp.setTime(LocalTime.parse(message.getMessage().getText()));

        notificationService.addTemp(message.getMessage().getChatId(), temp);


        return SendMessage.builder().chatId(String.valueOf(message.getMessage().getChatId()))
                .text("Введите переодичность: " + String.join("\n", parsePeriod.getTemplates()))
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
