package ru.hzerr.config.listener;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.event.EventListener;
import ru.hzerr.config.PropertyNames;

public abstract class BaseEventListener<T> implements EventListener<ConfigurationEvent> {

    private final String propertyName;

    public BaseEventListener(PropertyNames propertyName) { this.propertyName = propertyName.getName(); }

    public abstract void onRun(T newValue);

    @Override
    public void onEvent(ConfigurationEvent event) {
        if (event.getPropertyName().equals(propertyName)) {
            onRun((T) event.getPropertyValue());
        }
    }

    @CanIgnoreReturnValue
    public BaseEventListener<T> fire(T newValue) {
        onRun(newValue);
        return this;
    }
}
