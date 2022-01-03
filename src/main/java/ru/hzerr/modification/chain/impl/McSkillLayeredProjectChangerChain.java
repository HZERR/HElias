package ru.hzerr.modification.chain.impl;

import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.modification.chain.Sashok724LayeredProjectChangerChain;
import ru.hzerr.modification.chain.stage.impl.mcskill.*;
import ru.hzerr.modification.state.impl.McSkillState;
import ru.hzerr.modification.state.strategy.State;

public class McSkillLayeredProjectChangerChain extends Sashok724LayeredProjectChangerChain {

    private VBox processStateVBox;

    public McSkillLayeredProjectChangerChain(Profile profile, VBox vBox) {
        super(profile);
        this.processStateVBox = vBox;
    }

    @Override
    public <T extends State> void init(@NotNull T state) {
        if (processStateVBox == null) throw new NullPointerException("VBox can't be null!!!");
        if (state instanceof McSkillState) {
            if (((McSkillState) state).isChangeBackground()) addOnChangeBackground(new McSkillChangeBackgroundStage(processStateVBox));
            if (((McSkillState) state).isConstruct()) addOnBuild(new McSkillBuildStage(processStateVBox));
            if (((McSkillState) state).isDecompress()) addOnDecompress(new McSkillDecompressionStage(processStateVBox));
            if (((McSkillState) state).isCleanupProjectFolder()) addOnCleanupProjectFolder(new McSkillCleanupProjectDirectoryStage(processStateVBox));
            if (((McSkillState) state).isDeleteSecurity()) addOnRemoveSecurity(new McSkillRemoveSecurityStage(processStateVBox));
            if (((McSkillState) state).isDeleteBuildFile()) addOnRemoveBuildFile(new McSkillRemoveBuildFileStage(processStateVBox));
            if (((McSkillState) state).isRebuild()) addOnRebuild(new McSkillRebuildStage(processStateVBox));
            if (((McSkillState) state).isReplaceRuntimeFolder()) addOnUpdateRuntimeFolder(new McSkillRefreshRuntimeDirectoryStage(processStateVBox));
            if (((McSkillState) state).isPrependJFoenix()) addOnAppendJFoenix(new McSkillPrependJFoenixStage(processStateVBox));
            if (((McSkillState) state).isRemoveJFoenix()) addOnDeleteJFoenix(new McSkillRemoveJFoenixStage(processStateVBox));
        } else throw new IllegalStateException("The chain for the project is incorrectly selected. " +
                "The current state is not correct for this chain");
    }
}
