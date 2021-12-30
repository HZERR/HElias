package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.Launcher;

/**
 * The project launch stage
 */
public abstract class LaunchStage extends BaseStage<Void> {

    public LaunchStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        Launcher.create().apply(profile.getStructureProperty().getValue().getBuildFile().getLocation());
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred at the launch of the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.START.getLevel();
    }

    @Override
    public String description() {
        return "The project launch stage";
    }
}
