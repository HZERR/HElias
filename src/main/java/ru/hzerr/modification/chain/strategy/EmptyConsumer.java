package ru.hzerr.modification.chain.strategy;

import io.reactivex.rxjava3.functions.Consumer;

public interface EmptyConsumer<T> extends Consumer<T> {

    default Object emptyAccept(T t) throws Throwable {
        accept(t);
        return null;
    }
}
