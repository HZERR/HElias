package ru.hzerr.modification.util;

import net.lingala.zip4j.ZipFile;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.stream.FileStream;
import ru.hzerr.log.LogManager;

public class Builder {

    private BaseFile newZipFile;

    private Builder() {}

    public void setNewJarFile(BaseFile newJarFile) { this.newZipFile = newJarFile; }
    public BaseFile getNewJarFile() { return this.newZipFile; }

    public void apply(FileStream filesToBuild) {
        LogManager.getLogger().debug("Builder was started");
        ZipFile project = new ZipFile(newZipFile.asIOFile());
        filesToBuild.forEach(dir -> LogManager.getLogger().debug('\t' + dir.getLocation()));
        filesToBuild
                .dirForEach(dir -> project.addFolder(dir.asIOFile()))
                .fileForEach(file -> project.addFile(file.asIOFile()));
    }

    public static Builder create() { return new Builder(); }
}
