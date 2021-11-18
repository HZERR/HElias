package ru.hzerr.loaders.css;

public enum LoadType {
    ROOT("root"),
    TAB("tab"),
    POPUP("popup");

    private final String prefix;

    LoadType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() { return this.prefix; }
}
