package lk.pubudu.app.advice;

import lk.pubudu.app.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, Object> notFoundExceptionHandler(NotFoundException exp){
        Map<String, Object> errAttributes = new LinkedHashMap<>();
        errAttributes.put("status", HttpStatus.NOT_FOUND.value());
        errAttributes.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        errAttributes.put("message", exp.getMessage());
        errAttributes.put("timestamp", new Date().toString());
        return errAttributes;
    }
}
