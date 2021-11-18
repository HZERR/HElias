package ru.hzerr.log;

import org.apache.commons.configuration2.ex.ConfigurationException;
import ru.hzerr.HElias;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.SizeType;
import ru.hzerr.log.formatter.ColoredLogFormatter;
import ru.hzerr.log.formatter.LogFormatter;

import java.io.Closeable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: 05.11.2021 switch to logback
public final class SessionLogManager extends LogManager implements Closeable {

    private BaseFile sessionLogFile;
    private Logger logger;
    private static final SessionLogManager INSTANCE = new SessionLogManager();

    private SessionLogManager() {
    }

    public void createSession() throws IOException, ConfigurationException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        this.sessionLogFile = HElias.getProperties().getLogDir().createSubFile(formatter.format(Calendar.getInstance().getTime()));
        if (sessionLogFile.notExists()) throw new ConfigurationException("Unable to create a log file of the current session");
        this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        final ConsoleHandler CONSOLE_HANDLER = new ConsoleHandler();
        CONSOLE_HANDLER.setFormatter(new ColoredLogFormatter());
        CONSOLE_HANDLER.setLevel(Level.ALL);
        final FileHandler FILE_HANDLER = new FileHandler(sessionLogFile.getLocation());
        FILE_HANDLER.setFormatter(new LogFormatter());
        FILE_HANDLER.setLevel(Level.WARNING);
//        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        this.logger.addHandler(FILE_HANDLER);
        this.logger.addHandler(CONSOLE_HANDLER);
        this.logger.setUseParentHandlers(false);
        this.logger.setLevel(Level.ALL);
    }

    @Override
    public Logger getLogger() { return this.logger; }

    @Override
    public void close() {
        if (sessionLogFile.sizeOf(SizeType.BYTE) == 0D) {
            sessionLogFile.deleteOnExit();
        }
    }

    public static SessionLogManager getManager() { return INSTANCE; }
}
