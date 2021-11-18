package ru.hzerr.modification.util;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.HFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

public class ManifestChanger {

    private ManifestChanger() {}

    public void apply(BaseDirectory META_INF) throws IOException {
        HFile MANIFEST = META_INF.getSubFile( "MANIFEST.MF");
        HFile RSA_FILE = META_INF.getSubFile( "LAUNCHER.RSA");
        HFile SF_FILE  = META_INF.getSubFile( "LAUNCHER.SF");
        if (RSA_FILE.exists()) RSA_FILE.delete();
        if (SF_FILE.exists()) SF_FILE.delete();

        String multiline = MANIFEST.readLines(StandardCharsets.UTF_8)
                .filter(line -> line.contains("Manifest-Version:") || line.contains("Main-Class:"))
                .map(line -> line = line.concat("\r\n"))
                .collect(Collectors.joining());
        MANIFEST.writeLines(Collections.singleton(multiline));
    }

    public static ManifestChanger create() { return new ManifestChanger(); }
}
