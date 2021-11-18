package ru.hzerr.log;

import java.util.logging.Logger;

public abstract class LogManager {

    protected LogManager() {
    }

    public abstract Logger getLogger();
}
