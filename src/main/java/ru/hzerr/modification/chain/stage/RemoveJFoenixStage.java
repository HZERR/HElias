package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.JFoenixDeleter;

/**
 * The stage of removing the JFoenix library from the project
 */
public abstract class RemoveJFoenixStage extends BaseStage<Void> {

    public RemoveJFoenixStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        JFoenixDeleter.create().apply(profile.getStructureProperty().getValue().getBuildFile());
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred when removing the JFoenix library from the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.ADD_DELETE_LIBRARIES.getLevel();
    }

    @Override
    public String description() {
        return "The stage of removing the JFoenix library from the project";
    }
}
