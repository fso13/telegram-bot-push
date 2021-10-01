package ru.drudenko.telegrambotpush.impl.period;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EndMountPeriod implements Period {
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
        if (start.lengthOfMonth() == start.getDayOfMonth()) {
            return start;
        } else {
            return start.plusMonths(1).withDayOfMonth(start.plusMonths(1).lengthOfMonth());
        }
    }

    @Override
    public String getTemplate() {
        return "конец каждого месяца";
    }
}
