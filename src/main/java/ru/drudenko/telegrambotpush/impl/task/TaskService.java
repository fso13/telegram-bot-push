package ru.drudenko.telegrambotpush.impl.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.drudenko.telegrambotpush.impl.Bot;
import ru.drudenko.telegrambotpush.model.NotificationRepository;
import ru.drudenko.telegrambotpush.model.Status;

import java.time.LocalDateTime;

@EnableScheduling
@Service
public class TaskService {
    private final Bot bot;
    private final NotificationRepository notificationRepository;

    public TaskService(Bot bot,
                       NotificationRepository notificationRepository) {
        this.bot = bot;
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 5_000)
    public void process() {
        notificationRepository.findAllByStatus(Status.NEW).stream()
                .filter(task -> task.getNext().isBefore(LocalDateTime.now()))
                .forEach(task -> {
                    try {
                        bot.execute(SendMessage.builder().chatId(String.valueOf(task.getChatId()))
                                .text(task.getTitle())
                                .build());
                        task.setStatus(Status.SENT);
                        notificationRepository.save(task);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                });
    }
}
