package com.bridgelabz.exception;

public class StateCensusAnalyserException extends Exception {
    public enum Exceptiontype {
        ENTERED_WRONG_FILE_TYPE, ENTERED_INCORRECT_DELIMITER, ENTERED_WRONG_FILE,
        ENTERED_INCORRECT_DELIMITER_OR_HEADER, UNABLE_TO_PARSE,ILLEGAL_STATE;
    }

    public Exceptiontype type;

    public StateCensusAnalyserException(Exceptiontype type, String message) {
        super(message);
        this.type = type;
    }
}

