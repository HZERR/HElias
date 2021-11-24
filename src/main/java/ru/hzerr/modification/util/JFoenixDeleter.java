package ru.hzerr.modification.util;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import ru.hzerr.file.BaseFile;
import ru.hzerr.log.LogManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public class JFoenixDeleter {

    private static final Pattern JFOENIX_PATTERN = Pattern.compile("^com\\W{1,2}jfoenix\\W{1,2}$");

    private JFoenixDeleter() {
    }

    public void apply(BaseFile buildProjectFile) throws ZipException {
        ZipFile projectZip = new ZipFile(buildProjectFile.asIOFile());
        List<FileHeader> headers = new CopyOnWriteArrayList<>(projectZip.getFileHeaders());
        headers.removeIf(header -> !JFOENIX_PATTERN.matcher(header.getFileName()).matches());
        if (headers.size() == 0) throw new IllegalArgumentException("The JFoenix library was not found");
        LogManager.getLogger().debug("Deleting the " + headers.get(0).getFileName() + " header");
        projectZip.removeFile(headers.get(0));
    }

    public static JFoenixDeleter create() {
        return new JFoenixDeleter();
    }
}
