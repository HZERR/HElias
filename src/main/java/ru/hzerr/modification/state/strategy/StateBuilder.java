package ru.hzerr.modification.state.strategy;

import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.modification.state.impl.BorealisStateBuilder;
import ru.hzerr.modification.state.impl.McSkillStateBuilder;
import ru.hzerr.modification.state.impl.MythicalWorldStateBuilder;

public abstract class StateBuilder {

    protected final HMap<String, Object> values;

    public StateBuilder() { this.values = new HashHMap<>(); }

    public abstract State build();
    public abstract State buildDefaultState();

    public static StateBuilder getStateBuilderByType(ProjectType type) {
        switch (type) {
            case MC_SKILL:
                return new McSkillStateBuilder();
            case MYTHICAL_WORLD:
                return new MythicalWorldStateBuilder();
            case BOREALIS:
                return new BorealisStateBuilder();
            default:
                throw new IllegalArgumentException("Can't be handled current project type");
        }
    }
}
