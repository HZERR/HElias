package ru.hzerr.controller.actions.projects.mc.skill;

import javafx.beans.value.ObservableValue;
import ru.hzerr.config.listener.WrapperChangeListener;
import ru.hzerr.modification.state.impl.McSkillState;
import ru.hzerr.modification.state.strategy.StateManager;

public class McSkillDeleteSecurityChangeListener extends WrapperChangeListener<Boolean> {

    public McSkillDeleteSecurityChangeListener() {
        super(true);
    }

    @Override
    public void wrapChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        StateManager.getInstance().changeState(McSkillState.class, state -> state.setDeleteSecurity(newValue));
    }
}
