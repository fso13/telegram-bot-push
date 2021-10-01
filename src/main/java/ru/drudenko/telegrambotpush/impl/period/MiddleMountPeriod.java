package ru.drudenko.telegrambotpush.impl.period;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MiddleMountPeriod implements Period {
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
        if (start.lengthOfMonth() / 2 == start.getDayOfMonth()) {
            return start;
        } else {
            return start.plusMonths(1).withDayOfMonth(start.plusMonths(1).lengthOfMonth() / 2);
        }
    }

    @Override
    public String getTemplate() {
        return "середина каждого месяца";
    }
}
