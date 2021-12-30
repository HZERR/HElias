package ru.hzerr.modification.chain.stage;

import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;

/**
 * The base stage of the project transformation
 */
public abstract class BaseTransformationStage<T> extends BaseStage<T> {

    public BaseTransformationStage() {
        super();
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        ErrorSupport.showErrorPopup(throwable);
    }

    @Override
    public Integer level() {
        return Level.TRANSFORM.getLevel();
    }

    @Override
    public String description() {
        return "Project transformation stage";
    }
}
