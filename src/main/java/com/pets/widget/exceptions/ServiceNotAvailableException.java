package com.pets.widget.exceptions;

public class ServiceNotAvailableException extends RuntimeException {
    public ServiceNotAvailableException() {
        super("Sorry! Service not available now. Please, try again later \uD83D\uDE1E ");
    }
}
