package ru.hzerr.modification.state.strategy;

import ru.hzerr.modification.state.impl.BorealisState;
import ru.hzerr.modification.state.impl.McSkillState;
import ru.hzerr.modification.state.impl.MythicalWorldState;

public enum ProjectType {
    MC_SKILL(McSkillState.class),
    MYTHICAL_WORLD(MythicalWorldState.class),
    BOREALIS(BorealisState.class);

    private final Class<?> stateClass;

    ProjectType(Class<?> stateClass) {
        this.stateClass = stateClass;
    }

    public <T extends State> Class<T> getProjectType() {
        //noinspection unchecked
        return (Class<T>) this.stateClass;
    }

    @Override
    public String toString() {
        return stateClass.getSimpleName().substring(0, stateClass.getSimpleName().length() - 5);
    }
}
