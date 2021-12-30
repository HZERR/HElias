package ru.hzerr.modification.chain;

import io.reactivex.rxjava3.core.Observable;
import ru.hzerr.collections.list.HList;
import ru.hzerr.config.profile.Profile;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The lower the level of the action, the faster it will start
 * @author HZERR
 */
public abstract class AbstractLayeredProjectChangerChain {

    protected Observable<Profile> observable;
    protected HList<BaseStage<?>> stages = HList.newList();

    public AbstractLayeredProjectChangerChain(Profile profile) { observable = Observable.just(checkNotNull(profile)); }

    abstract void apply();

    protected void addStage(BaseStage<?> stage) {
        stages.add(checkNotNull(stage));
    }
}
