package ru.hzerr.modification.state.strategy;

/**
 * @see ru.hzerr.modification.state.impl.MythicalWorldStateBuilder
 * @see ru.hzerr.modification.state.impl.McSkillStateBuilder
 */
public enum DefaultParameters {
    DEBUG_MODE(true),
    PREPEND_JFOENIX(false),
    REMOVE_JFOENIX(false),
    UPDATE_RUNTIME_FOLDER(false),
    CHANGE_BACKGROUND(false),
    DELETE_SECURITY(true),
    REBUILD(false),
    DECOMPRESS(true),
    DELETE_BUILD_FILE(true),
    DELETE_PROJECT_FOLDER(true),
    CONSTRUCT(true);

    private final boolean defaultValue;

    DefaultParameters(boolean value) { this.defaultValue = value; }

    public boolean isEnabled() { return this.defaultValue; }
}
