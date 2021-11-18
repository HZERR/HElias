package ru.hzerr.modification.chain.strategy;

public interface BiConsumer<T, U> {

    void onAccept(T t, U u) throws Exception;

    default Object accept(T t, U u) throws Exception {
        onAccept(t, u);
        return null;
    }
}
