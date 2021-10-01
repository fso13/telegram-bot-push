package ru.drudenko.telegrambotpush.impl.period;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StartMountPeriod implements Period {
    @Override
    public boolean isCron() {
        return true;
    }

    @Override
    public boolean isSupport(String period) {
        return period.equals(getTemplate());
    }

    @Override
    public LocalDate parse(LocalDate start, String period) {
        if (start.getDayOfMonth() == 1) {
            return start;
        } else {
            return start.plusMonths(1).withDayOfMonth(1);
        }
    }

    @Override
    public String getTemplate() {
        return "начало каждого месяца";
    }
}
