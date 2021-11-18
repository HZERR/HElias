package ru.hzerr.config.profile;

import com.google.common.base.Preconditions;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;

import java.io.IOException;
import java.io.Serializable;

// only create
public class Structure implements Serializable {

    private final BaseDirectory rootDir;
    private final BaseDirectory decompressionDir; // hfiles
    private final BaseFile original;
    private BaseFile build;

    public Structure(BaseDirectory rootDir, BaseFile original) {
        this.rootDir = rootDir;
        this.original = original;
        this.decompressionDir = rootDir.getSubDirectory("hfiles");
    }

    public void setBuildName(String fullBuildName) {
        build = rootDir.getSubFile(fullBuildName);
    }

    public BaseFile getBuildFile() { return build; }
    public BaseFile getCommercialProjectJarFile() { return original; }
    public BaseDirectory getRootDir() { return rootDir; }

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
        rootDir.createSubDirectory("hfiles");
        return new Structure(rootDir, rootDir.getSubFile(original.getName()));
    }
}
