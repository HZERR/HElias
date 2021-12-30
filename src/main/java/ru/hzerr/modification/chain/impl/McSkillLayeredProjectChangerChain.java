package ru.hzerr.modification.chain.impl;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.modification.chain.Sashok724LayeredProjectChangerChain;
import ru.hzerr.modification.chain.stage.impl.mcskill.*;
import ru.hzerr.modification.state.impl.McSkillState;
import ru.hzerr.modification.state.strategy.State;

public class McSkillLayeredProjectChangerChain extends Sashok724LayeredProjectChangerChain {

    public McSkillLayeredProjectChangerChain(Profile profile) {
        super(profile);
    }

    @Override
    public <T extends State> void init(@NotNull T state) {
        if (state instanceof McSkillState) {
            if (((McSkillState) state).isChangeBackground()) addOnChangeBackground(new McSkillChangeBackgroundStage());
            if (((McSkillState) state).isConstruct()) addOnBuild(new McSkillBuildStage());
            if (((McSkillState) state).isDecompress()) addOnDecompress(new McSkillDecompressionStage());
            if (((McSkillState) state).isCleanupProjectFolder()) addOnCleanupProjectFolder(new McSkillCleanupProjectDirectoryStage());
            if (((McSkillState) state).isDeleteSecurity()) addOnRemoveSecurity(new McSkillRemoveSecurityStage());
            if (((McSkillState) state).isDeleteBuildFile()) addOnRemoveBuildFile(new McSkillRemoveBuildFileStage());
            if (((McSkillState) state).isRebuild()) addOnRebuild(new McSkillRebuildStage());
            if (((McSkillState) state).isReplaceRuntimeFolder()) addOnUpdateRuntimeFolder(new McSkillRefreshRuntimeDirectoryStage());
            if (((McSkillState) state).isPrependJFoenix()) addOnAppendJFoenix(new McSkillPrependJFoenixStage());
            if (((McSkillState) state).isRemoveJFoenix()) addOnDeleteJFoenix(new McSkillRemoveJFoenixStage());
        }
    }
}
