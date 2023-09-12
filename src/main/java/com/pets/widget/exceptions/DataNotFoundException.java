package com.pets.widget.exceptions;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super("Sorry! No information was found for your request \uD83E\uDD14 Please, check the correctness and try " +
            "again");
    }
}
