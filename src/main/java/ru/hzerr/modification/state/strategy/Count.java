package ru.hzerr.modification.state.strategy;

public interface Count {

    int count(State state);

    enum State {
        ENABLED,
        DISABLED,
        ALL
    }
}
