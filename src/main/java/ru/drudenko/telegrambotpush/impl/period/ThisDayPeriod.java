package ru.drudenko.telegrambotpush.impl.period;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ThisDayPeriod implements Period {
    private static final String D_2_D_2_D_4 = "^\\d{2}.\\d{2}.\\d{4}$";
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("dd.MM.yyyy")
            .toFormatter(Locale.forLanguageTag("RU"));

    @Override
    public boolean isCron() {
        return false;
    }

    @Override
    public boolean isSupport(String period) {
        return period.matches(D_2_D_2_D_4);
    }

    @Override
    public LocalDate parse(LocalDate start, String period) {
        return LocalDate.parse(period, formatter);
    }

    @Override
    public String getTemplate() {
        return "dd.MM.yyyy";
    }
}
