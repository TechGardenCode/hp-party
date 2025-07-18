package gg.techgarden.houseparty.party.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public ResponseEntity<String> handleHttpExceptions(HttpStatusCodeException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getResponseBodyAsString());
    }
}