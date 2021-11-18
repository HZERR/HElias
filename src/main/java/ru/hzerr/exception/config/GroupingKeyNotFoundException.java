package ru.hzerr.exception.config;

public class GroupingKeyNotFoundException extends UncheckedConfigurationException {

    public GroupingKeyNotFoundException() {
        super();
    }
    public GroupingKeyNotFoundException(String message) {
        super(message);
    }
    public GroupingKeyNotFoundException(String groupName, String key) {
        super("Key " + key + " is not found in group " + groupName);
    }
}
