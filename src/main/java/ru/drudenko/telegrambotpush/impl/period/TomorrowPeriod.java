package ru.drudenko.telegrambotpush.impl.period;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TomorrowPeriod implements Period {
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
        return start.plusDays(1);
    }

    @Override
    public String getTemplate() {
        return "завтра";
    }
}
