package ru.hzerr.exception.config;

public class GroupingKeyAlreadyExistsException extends UncheckedConfigurationException {

    public GroupingKeyAlreadyExistsException() {
        super();
    }
    public GroupingKeyAlreadyExistsException(String message) {
        super(message);
    }
    public GroupingKeyAlreadyExistsException(Throwable cause) { super(cause); }
    public GroupingKeyAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    public GroupingKeyAlreadyExistsException(String groupName, String key) {
        super("The key " + key + " already exists in group " + groupName);
    }
}
