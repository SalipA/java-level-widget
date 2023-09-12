package com.pets.widget.exceptions;

import com.pets.widget.components.WidgetController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {WidgetController.class})
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleDataNotFoundExp(DataNotFoundException exp) {
        return ResponseEntity.badRequest().body(exp.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleServiceNotAvailableExp(ServiceNotAvailableException exp) {
        return ResponseEntity.internalServerError().body(exp.getMessage());
    }
}
