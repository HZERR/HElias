package ru.hzerr.modification.state.strategy;

import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;

import java.util.Optional;
import java.util.function.Consumer;

public class StateManager {

    private final HList<State> states;
    private static final StateManager INSTANCE = new StateManager();

    private StateManager() {
        this.states = new ArrayHList<>();
    }

    public void addState(StateBuilder builder) {
        states.add(builder.build());
    }
    public void addIfAbsent(StateBuilder builder) {
        State state = builder.build();
        if (states.noContains(innerState -> innerState.getName().equals(state.getName()))) {
            states.add(state);
        }
    }

    public <T extends State> void removeState(Class<T> classToRemove) {
        states.removeIf(state -> state.getClass().isAssignableFrom(classToRemove));
    }

    @SuppressWarnings("unchecked")
    public <T extends State> void changeState(Class<T> clazz, Consumer<? super T> action) {
        states.find(innerState -> innerState.getClass().isAssignableFrom(clazz))
                .ifPresent(state -> action.accept((T) state));
    }

    @SuppressWarnings("unchecked")
    public <T extends State> Optional<T> findState(Class<T> clazz) {
        return (Optional<T>) states.find(innerState -> innerState.getClass().isAssignableFrom(clazz));
    }

    public <T extends State> Optional<T> findState(ProjectType type) {
        return (Optional<T>) states.find(innerState -> innerState.getClass().isAssignableFrom(type.getProjectType()));
    }

    public static StateManager getInstance() { return INSTANCE; }
}
