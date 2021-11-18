package ru.hzerr.exception.config;

public class UncheckedConfigurationException extends RuntimeException {

    public UncheckedConfigurationException() {
        super();
    }
    public UncheckedConfigurationException(String message) {
        super(message);
    }
    public UncheckedConfigurationException(Throwable cause) { super(cause); }
    public UncheckedConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
