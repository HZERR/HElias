package ru.hzerr.modification.chain;

import ru.hzerr.config.profile.Profile;

public abstract class BaseStage<T> {

    public BaseStage() {
    }

    public abstract void onStart();
    public abstract T run(Profile profile) throws Throwable;
    public abstract void onExit(Object result);
    public abstract <X extends Throwable> void onError(X throwable);
    public abstract Integer level();
    public abstract String description();
}
