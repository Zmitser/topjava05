package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;


public class FromStringToLocalDateTime implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        return TimeUtil.parseLocalDateTime(s);
    }
}
