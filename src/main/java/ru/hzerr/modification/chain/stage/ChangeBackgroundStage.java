package ru.hzerr.modification.chain.stage;

import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;

/**
 * The stage of changing the wallpaper of the project
 */
public abstract class ChangeBackgroundStage extends BaseStage<Void> {

    public ChangeBackgroundStage() {
        super();
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred when changing the project wallpaper");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.CHANGE_RESOURCES.getLevel();
    }

    @Override
    public String description() {
        return "The stage of changing the wallpaper of the project";
    }
}
