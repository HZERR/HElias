package ru.hzerr.config;

public enum PropertyNames {
    LANGUAGE("language"),
    THEME("theme"),
    EXPERT_MODE("expert.mode"),
    USE_CLASSES_FROM_BUILD_FILE("use.classes.from.build.file"),
    PATH_TO_INSTALLED_PROJECT("path.to.installed.project");

    private final String propertyName;

    PropertyNames(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getName() { return this.propertyName; }
}
