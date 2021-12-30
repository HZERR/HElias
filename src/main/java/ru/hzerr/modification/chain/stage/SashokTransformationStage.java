package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.annotation.OnlyProject;
import ru.hzerr.modification.util.SashokTransformator;

/**
 * The transformation stage of the Sashok724 project
 */
@OnlyProject("Sashok724")
public abstract class SashokTransformationStage extends BaseTransformationStage<Void> {

    public SashokTransformationStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        SashokTransformator.create().apply(profile);
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred during the transformation of the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public String description() {
        return "The transformation stage of the Sashok274 project";
    }
}
