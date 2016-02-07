package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Data is not valid")
public class ValidationException extends RuntimeException {

    public ValidationException(BindingResult result) {
        super(getStringBindingResult(result));
    }

    public static String getStringBindingResult(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
        return sb.toString();
    }
}
