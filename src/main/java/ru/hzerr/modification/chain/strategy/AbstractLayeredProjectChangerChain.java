package ru.hzerr.modification.chain.strategy;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import ru.hzerr.collections.HPair;
import ru.hzerr.modification.Project;

import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The lower the level of the action, the faster it will start
 * @author HZERR
 */
public abstract class AbstractLayeredProjectChangerChain {

    protected Observable<Project> observable;
    // maybe class "Data"???
    protected Multimap<Integer, HPair<Function<? super Project, Object>, Consumer<Object>>> actions = HashMultimap.create();

    public AbstractLayeredProjectChangerChain(Project project) { observable = Observable.just(checkNotNull(project)); }

    abstract void apply();

    protected void addFunction(Function<? super Project, Object> onAction, Consumer<Object> onFinished, Integer level) {
        actions.put(checkNotNull(level), HPair.create(checkNotNull(onAction), onFinished));
    }
}
