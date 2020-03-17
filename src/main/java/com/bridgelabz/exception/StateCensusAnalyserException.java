package com.bridgelabz.exception;

public class StateCensusAnalyserException extends Exception {
    public enum Exceptiontype {
        ENTERED_WRONG_FILE;
    }

    public Exceptiontype type;

    public StateCensusAnalyserException(Exceptiontype type, String message) {
        super(message);
        this.type = type;
    }
}

