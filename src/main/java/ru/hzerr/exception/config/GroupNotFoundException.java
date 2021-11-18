package ru.hzerr.exception.config;

public class GroupNotFoundException extends UncheckedConfigurationException {

    public GroupNotFoundException() {
        super();
    }
    public GroupNotFoundException(String groupName) {
        super("Group " + groupName + " was not found");
    }
}
