package ru.drudenko.telegrambotpush.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findAllByChatIdAndStatus(Long chatId, Status status);
    List<Notification> findAllByStatus(Status status);
    List<Notification> findAllByStatusAndCronIsTrue(Status status);
}
