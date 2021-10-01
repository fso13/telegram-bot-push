package ru.drudenko.telegrambotpush.impl.period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParsePeriodService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final List<Period> periods;

    public ParsePeriodService(List<Period> periods) {
        this.periods = periods;
    }

    public Pair<LocalDateTime, Boolean> parse(LocalDate createDate, LocalTime time, String periodString) {
        Period p = periods.stream()
                .filter(period -> period.isSupport(periodString))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

        LocalDate localDate = p.parse(createDate, periodString);


        LocalDateTime localDateTime = localDate.atTime(time);
        log.info(" createDate:{},  time:{},  periodString:{}, LocalDateTime:{}", createDate
                , time, periodString, localDateTime);
        return Pair.of(localDateTime, p.isCron());
    }

    public List<String> getTemplates() {
        return periods.stream()
                .map(Period::getTemplate)
                .collect(Collectors.toList());
    }
}
