package ru.hzerr.config.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;

public abstract class WrapperChangeListener<T> implements ChangeListener<T> {

    private final boolean shouldShowErrorPopup;

    public WrapperChangeListener() {
        this.shouldShowErrorPopup = false;
    }
    public WrapperChangeListener(boolean shouldShowErrorPopup) {
        this.shouldShowErrorPopup = shouldShowErrorPopup;
    }

    public abstract void wrapChanged(ObservableValue<? extends T> observable, T oldValue, T newValue);

    @Override
    public final void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        try {
            wrapChanged(observable, oldValue, newValue);
        } catch (Throwable throwable) {
            if (shouldShowErrorPopup) {
                ErrorSupport.showErrorPopup(throwable);
            } else LogManager.getLogger().error("There was an error in the listener", throwable);
        }
    }
}
