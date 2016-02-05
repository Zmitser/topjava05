package ru.javawebinar.topjava.util.converter;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Created by Dimka on 02.02.2016.
 */
public class LocalDateTimeFormatterTwo implements Formatter<LocalDateTime> {
    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
        return TimeUtil.parseLocalDateTime(s);
    }

    @Override
    public String print(LocalDateTime dateTime, Locale locale) {
        return dateTime.format(TimeUtil.DATE_TME_FORMATTER);
    }


}
