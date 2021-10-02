package ru.drudenko.telegrambotpush.api;

import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.drudenko.telegrambotpush.impl.period.ParsePeriodService;
import ru.drudenko.telegrambotpush.model.Notification;
import ru.drudenko.telegrambotpush.model.NotificationRepository;
import ru.drudenko.telegrambotpush.model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NotificationService {
    private final Map<Long, Notification> notificationsTempMap = new HashMap<>();
    private final NotificationRepository notificationRepository;
    private final ParsePeriodService parsePeriodService;

    public NotificationService(NotificationRepository notificationRepository,
                               ParsePeriodService parsePeriodService) {
        this.notificationRepository = notificationRepository;
        this.parsePeriodService = parsePeriodService;
    }

    public void add(Long chatId, Notification msg) {
        Pair<LocalDateTime, Boolean> parse = parsePeriodService.parse(LocalDate.now(), msg.getTime(), msg.getPeriod());

        Notification notification = new Notification();
        notification.setChatId(chatId);
        notification.setPeriod(msg.getPeriod());
        notification.setTime(msg.getTime());
        notification.setCron(parse.getSecond());
        notification.setOffset(msg.getOffset());
        notification.setNext(parse.getFirst().toInstant(ZoneOffset.ofTotalSeconds(msg.getOffset())));
        notification.setStatus(Status.NEW);
        notification.setTitle(msg.getTitle());
        notificationRepository.save(notification);
        msg.setNext(notification.getNext());
    }

    public void addTemp(Long chatId, Notification msg) {
        notificationsTempMap.put(chatId, msg);
    }

    public void removeTemp(Long chatId) {
        notificationsTempMap.remove(chatId);
    }

    public Notification getTempMessage(Long chatId) {
        return notificationsTempMap.get(chatId);
    }

    public List<Notification> getMessages(Long chatId) {
        return notificationRepository.findAllByChatIdAndStatus(chatId, Status.NEW);
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 5_000)
    public void process() {
        notificationRepository.findAllByStatusAndCronIsTrue(Status.SENT)
                .forEach(task -> {
                    Pair<LocalDateTime, Boolean> parse = parsePeriodService.parse(task.getNext().atZone(ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(task.getOffset()))).toLocalDate(), task.getTime(), task.getPeriod());
                    task.setStatus(Status.NEW);
                    task.setNext(parse.getFirst().toInstant(ZoneOffset.ofTotalSeconds(task.getOffset())));
                    notificationRepository.save(task);
                });
    }

}
