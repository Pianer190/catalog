package ru.rbt;

public class PropsRuntimeException extends RuntimeException {

    public PropsRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    public PropsRuntimeException(String message) {
        super(message);
    }
}