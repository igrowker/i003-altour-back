package com.igrowker.altour.exceptions;

public class InvalidInputException extends RuntimeException{

    private static final String DESCRIPTION = "Input invalido";

    public InvalidInputException(String detail) { super(DESCRIPTION + ". " + detail); }
}

