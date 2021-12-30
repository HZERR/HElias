package ru.hzerr.modification.chain.stage.combine;

import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;

/**
 * Project defense removal stage
 */
public abstract class RemoveSecurityStage extends BaseStage<Void> {

    public RemoveSecurityStage() {
        super();
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred during the deletion of the project defense");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.TRANSFORM.getLevel();
    }

    @Override
    public String description() {
        return "Project defense removal stage";
    }
}
