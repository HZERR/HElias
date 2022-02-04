package ru.hzerr.config.profile;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.exception.directory.HDirectoryRenameFailedException;
import ru.hzerr.file.exception.directory.HDirectoryRenameImpossibleException;

import java.io.IOException;
import java.io.Serializable;

// only create
public class Structure implements Serializable {

    private static final long serialVersionUID = 2L;

    private BaseDirectory rootDir;
    private BaseDirectory decompressionDir; // unpacked
    private BaseDirectory assetsDir;
    private BaseFile original;
    private BaseFile build;

    private Structure(@NotNull BaseDirectory rootDir, @NotNull BaseFile original) {
        this.rootDir = rootDir;
        this.original = original;
        this.decompressionDir = rootDir.getSubDirectory("unpacked");
        this.assetsDir = rootDir.getSubDirectory("assets");
    }

    public void setBuildName(String fullBuildName) {
        build = rootDir.getSubFile(fullBuildName);
    }

    // WARNING: SEE REALIZATION
    public void rename(String rootDirName) throws HDirectoryRenameFailedException, HDirectoryRenameImpossibleException {
        rootDir.rename(rootDirName);
        decompressionDir = rootDir.getSubDirectory("unpacked");
        original = rootDir.getSubFile(original.getName());
        build = rootDir.getSubFile(build.getName());
    }

    public BaseFile getBuildFile() { return build; }
    public BaseFile getCommercialProjectJarFile() { return original; }
    public BaseDirectory getRootDir() { return rootDir; }
    public BaseDirectory getDecompressionDir() { return decompressionDir; }
    public BaseDirectory getAssetsDir() { return assetsDir; }

    public void deleteBuildFile() throws IOException {
        if (build.exists()) {
            build.delete();
        }
    }

    public void cleanupDecompressDirectory() throws IOException { this.decompressionDir.clean(); }
    public void destroyStructure() throws IOException { this.rootDir.delete(); }

    public static Structure createStructure(BaseDirectory rootDir, BaseFile original) throws IOException {
        Preconditions.checkArgument(rootDir.exists());
        Preconditions.checkArgument(original.exists());
        original.copyToDirectory(rootDir);
        rootDir.createSubDirectory("unpacked");
        rootDir.createSubDirectory("assets");
        return new Structure(rootDir, rootDir.getSubFile(original.getName()));
    }
}
