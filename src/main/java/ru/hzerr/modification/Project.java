package ru.hzerr.modification;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.annotation.Preinstall;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.log.LogManager;

import java.io.IOException;

@Deprecated
public class Project {

    @Preinstall private BaseDirectory root;
    @Preinstall private BaseFile originalProjectFile;
    @Preinstall private BaseFile build;
    private BaseDirectory unpack;

    /*
     * root -> hfiles -> unpacked files...
     *      -> original project file
     */
    private Project(BaseFile original, BaseDirectory newRoot, String buildFileName, boolean isCreated) throws IOException {
        this.root = newRoot;
        if (!isCreated) {
            original.copyToDirectory(root);
            unpack = newRoot.createSubDirectory("hfiles");
        } else unpack = newRoot.getSubDirectory("hfiles");
        this.originalProjectFile = this.root.getSubFile(original.getName());
        this.build = this.root.getSubFile(buildFileName);
    }

    public void deleteProjectFile() throws IOException { originalProjectFile.delete(); }
    public void deleteBuildFile() throws IOException { build.delete(); }
    public void deleteBuildFileIfNeeded() throws IOException { if (build.exists()) build.delete(); }

    public boolean cleanBuildFiles() throws IOException {
        LogManager.getLogger().debug("Cleaning the build files...");
        unpack.getAllFiles(false).forEach(directory -> {
            LogManager.getLogger().debug("Deleting the file: " + directory.getLocation());
            directory.delete();
        });
        boolean directoriesDeleted = unpack.getDirectories().allMatch(BaseDirectory::notExists);
        boolean filesDeleted = unpack.getFiles().allMatch(BaseFile::notExists);
        return directoriesDeleted && filesDeleted;
    }

    public void delete() throws IOException { root.delete(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        return new EqualsBuilder().append(root, project.root).append(originalProjectFile, project.originalProjectFile).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(root).append(originalProjectFile).toHashCode();
    }

    public static Project create(@NotNull BaseFile original, @NotNull BaseDirectory newRoot, @NotNull String buildFileName) throws IOException {
        return new Project(original, newRoot, buildFileName, false);
    }

    public static Project getProject(@NotNull BaseFile original, @NotNull BaseDirectory newRoot, @NotNull String buildFileName) throws IOException {
        return new Project(original, newRoot, buildFileName, true);
    }

    public BaseDirectory getRoot() {
        return root;
    }

    public void setRoot(BaseDirectory root) {
        this.root = root;
    }

    public BaseFile getOriginalProjectFile() {
        return originalProjectFile;
    }

    public void setOriginalProjectFile(BaseFile originalProjectFile) {
        this.originalProjectFile = originalProjectFile;
    }

    public BaseFile getBuild() {
        return build;
    }

    public void setBuild(BaseFile build) {
        this.build = build;
    }

    public BaseDirectory getUnpack() {
        return unpack;
    }

    public void setUnpack(BaseDirectory unpack) {
        this.unpack = unpack;
    }
}
