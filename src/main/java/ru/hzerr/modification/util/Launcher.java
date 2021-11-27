package ru.hzerr.modification.util;

import ru.hzerr.HElias;
import ru.hzerr.file.BaseFile;

import java.io.IOException;

public class Launcher {

    private Launcher() {}

    public void apply(String pathToJarFile) throws IOException {
        if (HElias.getProperties().getDefaultProfile().isPresent()) {
            BaseFile java = HElias.getProperties().getDefaultProfile().get().getSettingsProperty().getValue().getGlobalSettings().getJava();
            if (java.exists()) {
                new ProcessBuilder(java.getLocation(), "-jar", pathToJarFile).inheritIO().start();
            }
        } else new ProcessBuilder("java", "-jar", pathToJarFile).inheritIO().start();
    }

    public static Launcher create() { return new Launcher(); }
}
