package ru.hzerr.log;

import ch.qos.logback.classic.Logger;

import java.io.Closeable;

public abstract class LogFactory implements Closeable {

    protected Logger log;

    public Logger getLogger() {
        return log;
    }
}
