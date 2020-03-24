package com.bridgelabz.exception;

public class CSVBuilderException extends Exception{
    public enum Exceptiontype {
         ENTERED_WRONG_FILE,ENTERED_WRONG_FILE_TYPE,
        ENTERED_INCORRECT_DELIMITER_OR_HEADER,ILLEGAL_STATE;
    }
    public Exceptiontype type;

    public CSVBuilderException(Exceptiontype type, String message) {
        super(message);
        this.type = type;
    }
}
