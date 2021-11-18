package ru.hzerr.modification.util;

import net.lingala.zip4j.ZipFile;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class JFoenixAppender {

    private BaseDirectory tempFolder;

    private JFoenixAppender() {}

    /**
     * JFoenixAppender удалит сам temp folder
     * @param tempFolder tmp directory
     */
    public void setTempFolder(BaseDirectory tempFolder) {
        this.tempFolder = tempFolder;
    }

    public BaseDirectory getTempFolder() {
        return this.tempFolder;
    }

    public void apply(BaseFile buildProjectFile) throws IOException {
        // copy in new folder
        BaseFile jfoenix8 = tempFolder.getSubFile( "jfoenix-8.0.10.jar"); // is not exists
        Files.copy(Objects.requireNonNull(JFoenixAppender.class.getResourceAsStream("/jfoenix-8.0.10.jar")), jfoenix8.asPath(), StandardCopyOption.REPLACE_EXISTING);
        // extract
        ZipFile jfoenix8Zip = new ZipFile(jfoenix8.asIOFile());
        jfoenix8Zip.extractAll(tempFolder.getLocation());
        // update
        ZipFile projectTestZipFile = new ZipFile(buildProjectFile.asIOFile());
        projectTestZipFile.addFolder(tempFolder.getSubDirectory("com").asIOFile());
        tempFolder.delete();
    }

    public static JFoenixAppender create() { return new JFoenixAppender(); }
}
