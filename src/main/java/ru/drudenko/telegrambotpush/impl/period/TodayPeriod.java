package ru.drudenko.telegrambotpush.impl.period;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TodayPeriod implements Period {
    @Override
    public boolean isCron() {
        return false;
    }

    @Override
    public boolean isSupport(String period) {
        return period.equals(getTemplate());
    }

    @Override
    public LocalDate parse(LocalDate start, String period) {
        return start;
    }

    @Override
    public String getTemplate() {
        return "сегодня";
    }
}
