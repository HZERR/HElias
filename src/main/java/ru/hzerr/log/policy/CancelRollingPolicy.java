package ru.hzerr.log.policy;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

import java.io.File;

public class CancelRollingPolicy extends TimeBasedRollingPolicy<ILoggingEvent> {

    @Override
    public boolean isTriggeringEvent(File activeFile, ILoggingEvent event) {
        return false;
    }
}
