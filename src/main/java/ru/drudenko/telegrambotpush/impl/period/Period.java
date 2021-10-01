package ru.drudenko.telegrambotpush.impl.period;

import java.time.LocalDate;

public interface Period {
    boolean isCron();

    boolean isSupport(String period);

    LocalDate parse(LocalDate start, String period);

    String getTemplate();
}
