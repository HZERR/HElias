package ru.hzerr.log.formatter;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class SimpleLogFormatter extends Formatter {

    private static final String format = "[%1$tF %1$tT] [%2$s] %3$s %n";

    @Override
    public synchronized String format(LogRecord lr) {
        return String.format(format,
                new Date(lr.getMillis()),
                lr.getLevel().getLocalizedName(),
                lr.getMessage()
        );
    }
}