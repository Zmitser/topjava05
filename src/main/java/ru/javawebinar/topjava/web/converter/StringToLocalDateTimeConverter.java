package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;

/**
 * Created by Dimka on 21.01.2016.
 */
public class StringToLocalDateTimeConverter  implements Converter<String, LocalDateTime> {

    public LocalDateTime convert(String param) {
        return TimeUtil.parseLocalDateTime(param);
    }
}
