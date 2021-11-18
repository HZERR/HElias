package ru.hzerr.modification.util;

import net.lingala.zip4j.ZipFile;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;

import java.io.File;
import java.util.function.Consumer;

public class Updater {

    private HList<String> filesToBeUpdated;
    private File folderWithFiles;
    private Consumer<Throwable> onError;

    private Updater() {}

    public void setFilesToBeUpdated(HList<String> filesToBeUpdated) { this.filesToBeUpdated = filesToBeUpdated; }
    public void setFilesToBeUpdated(String... files) { this.filesToBeUpdated = HList.of(files); }
    public HList<String> getFilesToBeUpdated() { return this.filesToBeUpdated; }
    public void setFolderWithFiles(BaseDirectory folderWithFiles) { this.folderWithFiles = folderWithFiles.asIOFile(); }
    public BaseDirectory getFolderWithFiles() { return new HDirectory(folderWithFiles.getAbsolutePath()); }
    public void setOnError(Consumer<Throwable> onError) { this.onError = onError; }

    public void apply(BaseFile projectFile) {
        ZipFile projectTestZip = new ZipFile(projectFile.asIOFile());
        filesToBeUpdated.toHStream()
                .map(name -> new File(folderWithFiles, name))
                .biForEach(File::isDirectory, projectTestZip::addFolder, projectTestZip::addFile);
    }

    public static Updater create() { return new Updater(); }
}
