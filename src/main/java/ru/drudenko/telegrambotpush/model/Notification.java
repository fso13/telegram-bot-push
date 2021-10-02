package ru.drudenko.telegrambotpush.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "tb_notification")
public class Notification {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column
    private String id;
    @Column(name = "chat_id")
    private Long chatId;
    @Column
    private String title;
    @Column
    private LocalTime time;
    @Column
    private String period;
    @Column
    private boolean cron;
    @Column(name = "offset_seconds")
    private int offset;
    @Column
    private Instant next;
    @Column(name = "create_date")
    private Instant createDate = Instant.now();
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Instant getNext() {
        return next;
    }

    public void setNext(Instant next) {
        this.next = next;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public boolean isCron() {
        return cron;
    }

    public void setCron(boolean cron) {
        this.cron = cron;
    }

    @Override
    public String toString() {
        return title + ", в " + time + " " + period;
    }
}
