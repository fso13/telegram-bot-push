package ru.drudenko.telegrambotpush.impl.command;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.drudenko.telegrambotpush.api.Command;

import java.util.Arrays;
import java.util.Collections;

@Service
public class DefaultCommand implements Command {

    public static final String COMMAND = "Основное меню";

    @Override
    public SendMessage process(Update message) {
        KeyboardButton keyboardButton1 = KeyboardButton.builder().text("Создать напоминалку").build();
        KeyboardRow row1 = new KeyboardRow(Collections.singletonList(keyboardButton1));
        KeyboardButton keyboardButton2 = KeyboardButton.builder().text("Список напоминалок").build();
        KeyboardRow row2 = new KeyboardRow(Collections.singletonList(keyboardButton2));

        return SendMessage.builder().chatId(String.valueOf(message.getMessage().getChatId()))
                .text("выбери")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboard(Arrays.asList(row1, row2))
                        .oneTimeKeyboard(true)
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
