package ru.hzerr.exception;

public class ProfileCreationException extends Exception {

    public ProfileCreationException() {
        super();
    }
    public ProfileCreationException(String message) {
        super(message);
    }
    public ProfileCreationException(Throwable cause) { super(cause); }
    public ProfileCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
