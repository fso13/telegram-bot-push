package ru.drudenko.telegrambotpush.impl.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.drudenko.telegrambotpush.api.Command;
import ru.drudenko.telegrambotpush.api.NotificationService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListCommand implements Command {
    public static final String COMMAND = "Список напоминалок";
    private final NotificationService notificationService;

    public ListCommand(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public SendMessage process(Update message) {
        List<InlineKeyboardButton> keyboard =  notificationService.getMessages(message.getMessage().getChatId())
                .stream().map(s -> InlineKeyboardButton.builder()
                        .text(s.toString())
                                .callbackData("list-notification")
                        .build()).collect(Collectors.toList());

        return SendMessage.builder().chatId(String.valueOf(message.getMessage().getChatId()))
                .text("Вот список ваших дел")
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(Collections.singletonList(keyboard))
                        .build())
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
