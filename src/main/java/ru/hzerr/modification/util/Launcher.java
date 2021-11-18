package ru.hzerr.modification.util;

import java.io.IOException;

public class Launcher {

    private Launcher() {}

    public void apply(String pathToJarFile) throws IOException {
        new ProcessBuilder("C:\\Program Files\\BellSoft\\LibericaJDK-8-Full\\bin\\java", "-jar", pathToJarFile).inheritIO().start();
    }

    public static Launcher create() { return new Launcher(); }
}
