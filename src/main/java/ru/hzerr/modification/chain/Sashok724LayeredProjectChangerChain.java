package ru.hzerr.modification.chain;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.modification.chain.stage.*;
import ru.hzerr.modification.chain.stage.combine.RebuildStage;
import ru.hzerr.modification.chain.stage.combine.RemoveSecurityStage;

public abstract class Sashok724LayeredProjectChangerChain extends BaseLayeredProjectChangerChain {

    public Sashok724LayeredProjectChangerChain(Profile profile) {
        super(profile);
    }

    public <T extends RemoveBuildFileStage>
    void addOnRemoveBuildFile(T stage) {
        super.addStage(stage);
    }

    public <T extends CleanupProjectDirectoryStage>
    void addOnCleanupProjectFolder(T stage) { super.addStage(stage); }

    public <T extends DecompressionStage>
    void addOnDecompress(T stage) { super.addStage(stage); }

    public <T extends ChangeManifestStage>
    void addOnChangeManifest(T stage) { super.addStage(stage); }

    public <T extends SashokTransformationStage>
    void addOnTransform(T stage) { super.addStage(stage); }

    public <T extends RemoveSecurityStage>
    void addOnRemoveSecurity(T stage) { super.addStage(stage); }

    public <T extends BuildStage>
    void addOnBuild(T stage) { super.addStage(stage); }

    public <T extends RebuildStage>
    void addOnRebuild(T stage) { super.addStage(stage); }

    public <T extends RefreshRuntimeDirectoryStage>
    void addOnUpdateRuntimeFolder(T stage) { super.addStage(stage); }

    public <T extends PrependJFoenixStage>
    void addOnAppendJFoenix(T stage) { super.addStage(stage); }

    public <T extends RemoveJFoenixStage>
    void addOnDeleteJFoenix(T stage) { super.addStage(stage); }

    public <T extends ChangeBackgroundStage>
    void addOnChangeBackground(T stage) { super.addStage(stage); }

    public <T extends LaunchStage>
    void addOnStart(T stage) { super.addStage(stage); }
}
