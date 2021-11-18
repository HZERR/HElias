package ru.hzerr.config.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.SessionLogManager;

public abstract class WrapperChangeListener<T> implements ChangeListener<T> {

    private final boolean shouldShowErrorPopup;

    public WrapperChangeListener(boolean shouldShowErrorPopup) {
        this.shouldShowErrorPopup = shouldShowErrorPopup;
    }

    public abstract void wrapChanged(ObservableValue<? extends T> observable, T oldValue, T newValue);

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        try {
            wrapChanged(observable, oldValue, newValue);
        } catch (Throwable throwable) {
            if (shouldShowErrorPopup) {
                ErrorSupport.showErrorPopup(throwable);
            } else
                SessionLogManager.getManager().getLogger().throwing(WrapperChangeListener.class.getSimpleName(), "changed(observable, oldValue, newValue)", throwable);
        }
    }
}
