package ru.hzerr.modification.chain.strategy;

public interface EmptyRunnable extends Runnable {

    default Object emptyRun() {
        run();
        return null;
    }
}
