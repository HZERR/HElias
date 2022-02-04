package ru.hzerr.modification.chain.impl;

import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.modification.chain.Sashok724LayeredProjectChangerChain;
import ru.hzerr.modification.chain.stage.impl.mythical.world.*;
import ru.hzerr.modification.state.impl.MythicalWorldState;
import ru.hzerr.modification.state.strategy.State;

public class MythicalWorldLayeredProjectChangerChain extends Sashok724LayeredProjectChangerChain {

    private VBox processStateVBox;

    public MythicalWorldLayeredProjectChangerChain(Profile profile, VBox vBox) {
        super(profile);
        this.processStateVBox = vBox;
    }

    @Override
    public <T extends State> void init(@NotNull T state) {
        if (processStateVBox == null) throw new NullPointerException("VBox can't be null!!!");
        if (state instanceof MythicalWorldState) {
            if (((MythicalWorldState) state).isChangeBackground()) addOnChangeBackground(new MythicalWorldChangeBackgroundStage(processStateVBox));
            if (((MythicalWorldState) state).isConstruct()) addOnBuild(new MythicalWorldBuildStage(processStateVBox));
            if (((MythicalWorldState) state).isDecompress()) addOnDecompress(new MythicalWorldDecompressionStage(processStateVBox));
            if (((MythicalWorldState) state).isCleanupProjectFolder()) addOnCleanupProjectFolder(new MythicalWorldCleanupProjectDirectoryStage(processStateVBox));
            if (((MythicalWorldState) state).isDeleteSecurity()) addOnRemoveSecurity(new MythicalWorldRemoveSecurityStage(processStateVBox));
            if (((MythicalWorldState) state).isDeleteBuildFile()) addOnRemoveBuildFile(new MythicalWorldRemoveBuildFileStage(processStateVBox));
            if (((MythicalWorldState) state).isRebuild()) addOnRebuild(new MythicalWorldRebuildStage(processStateVBox));
            if (((MythicalWorldState) state).isReplaceRuntimeFolder()) addOnUpdateRuntimeFolder(new MythicalWorldRefreshRuntimeDirectoryStage(processStateVBox));
            if (((MythicalWorldState) state).isPrependJFoenix()) addOnAppendJFoenix(new MythicalWorldPrependJFoenixStage(processStateVBox));
            if (((MythicalWorldState) state).isRemoveJFoenix()) addOnDeleteJFoenix(new MythicalWorldRemoveJFoenixStage(processStateVBox));
        } else throw new IllegalStateException("The chain for the project is incorrectly selected. " +
                "The current state is not correct for this chain");
    }
}
