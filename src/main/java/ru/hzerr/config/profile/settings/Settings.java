package ru.hzerr.config.profile.settings;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.modification.state.strategy.ProjectType;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.modification.state.strategy.StateBuilder;

import java.io.Serializable;
import java.util.function.Consumer;

public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    private GlobalSettings globalSettings;
    // warning may be all state
    private State state;

    public Settings() {
    }

    public GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

    public void setGlobalSettings(GlobalSettings globalSettings) {
        this.globalSettings = globalSettings;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public <T extends State> T getState(T clazz) {
        //noinspection unchecked
        return (T) state;
    }

    public <T extends State> void changeState(Class<T> clazz, Consumer<? super T> action) {
        if (state.getClass().isAssignableFrom(clazz)) {
            action.accept((T) state);
        } else
            throw new IllegalArgumentException("Current " + clazz.getSimpleName() + " can't be handled");
    }

    public static @NotNull Settings makeCopy(ProjectType type) {
        Settings settings = new Settings();
        settings.setGlobalSettings(GlobalSettings.makeCopy());
        settings.setState(StateBuilder.getStateBuilderByType(type).buildDefaultState());
        return settings;
    }
}
