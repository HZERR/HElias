package ru.hzerr.config.listener;

import ru.hzerr.config.PropertyNames;

public abstract class BooleanEventListener extends BaseEventListener<Boolean> {

    public BooleanEventListener(PropertyNames propertyName) {
        super(propertyName);
    }
}
