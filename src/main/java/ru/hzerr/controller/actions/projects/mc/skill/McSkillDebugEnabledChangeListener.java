package ru.hzerr.controller.actions.projects.mc.skill;

import javafx.beans.value.ObservableValue;
import ru.hzerr.HElias;
import ru.hzerr.config.listener.WrapperChangeListener;
import ru.hzerr.modification.state.impl.McSkillState;

public class McSkillDebugEnabledChangeListener extends WrapperChangeListener<Boolean> {

    public McSkillDebugEnabledChangeListener() {
        super(true);
    }

    @Override
    public void wrapChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        HElias.getProperties().getDefaultProfile().ifPresent(profile -> {
            profile.getSettingsProperty().getValue().changeState(McSkillState.class, state -> state.setDebugMode(newValue));
        });
    }
}
