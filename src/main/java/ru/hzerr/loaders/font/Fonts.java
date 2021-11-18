package ru.hzerr.loaders.font;

public enum Fonts {
    ZEN_TOKYO_ZOO("zentokyozoo/ZenTokyoZoo-Regular.ttf"),
    RUBIK("rubik/Rubik-Light.ttf"),
    RED_HAT("redhat/RedHatDisplay-Regular.ttf"),
    PLAY("play/Play-Regular.ttf");

    private final String path;

    Fonts(String path) { this.path = path; }

    public String getPath() { return this.path; }
}
