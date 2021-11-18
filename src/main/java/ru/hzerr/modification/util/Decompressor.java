package ru.hzerr.modification.util;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;

import java.util.concurrent.TimeUnit;

public class Decompressor {

    private BaseDirectory decompressionFolder;

    private Decompressor() {
    }

    public void setDecompressionFolder(BaseDirectory decompressionFolder) { this.decompressionFolder = decompressionFolder; }
    public BaseDirectory getDecompressionFolder() { return this.decompressionFolder; }

    public void apply(BaseFile projectJarFile) throws ZipException {
        while (decompressionFolder.isNotEmpty()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        new ZipFile(projectJarFile.asIOFile()).extractAll(decompressionFolder.getLocation());
    }

    public static Decompressor create() { return new Decompressor(); }
}
