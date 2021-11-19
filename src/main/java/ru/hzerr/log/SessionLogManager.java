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
    private FileHandler fileHandler;
    private ConsoleHandler consoleHandler;
    private static final SessionLogManager INSTANCE = new SessionLogManager();

    private SessionLogManager() {
    }

    public void createSession() throws IOException, ConfigurationException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        this.sessionLogFile = HElias.getProperties().getLogDir().createSubFile(formatter.format(Calendar.getInstance().getTime()));
        if (sessionLogFile.notExists()) throw new ConfigurationException("Unable to create a log file of the current session");
        this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColoredLogFormatter());
        consoleHandler.setLevel(Level.ALL);
        fileHandler = new FileHandler(sessionLogFile.getLocation());
        fileHandler.setFormatter(new LogFormatter());
        fileHandler.setLevel(Level.WARNING);
//        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        this.logger.addHandler(fileHandler);
        this.logger.addHandler(consoleHandler);
        this.logger.setUseParentHandlers(false);
        this.logger.setLevel(Level.ALL);
    }

    @Override
    public Logger getLogger() { return this.logger; }

    @Override
    public void close() {
        fileHandler.close();
        consoleHandler.close();
        if (sessionLogFile.sizeOf(SizeType.BYTE) == 0D) {
            sessionLogFile.deleteOnExit();
        }
    }

    public static SessionLogManager getManager() { return INSTANCE; }
}
