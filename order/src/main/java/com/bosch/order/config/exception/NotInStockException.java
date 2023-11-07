package com.bosch.order.config.exception;

public class NotInStockException extends Exception{
    public NotInStockException(String message) {
        super(message);
    }
}
