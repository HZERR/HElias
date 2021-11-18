package ru.hzerr.config.listener;

import ru.hzerr.config.PropertyNames;

public abstract class StringEventListener extends BaseEventListener<String> {

    public StringEventListener(PropertyNames propertyName) {
        super(propertyName);
    }
}
