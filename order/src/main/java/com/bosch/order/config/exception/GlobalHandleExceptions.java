package com.bosch.order.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandleExceptions {

    @ExceptionHandler(NotInStockException.class)
    public ResponseEntity<?> notInStockException(NotInStockException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
