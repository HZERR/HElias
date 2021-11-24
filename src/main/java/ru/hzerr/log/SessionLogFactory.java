package ru.hzerr.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.LoggerFactory;
import ru.hzerr.HElias;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.SizeType;
import ru.hzerr.log.encoder.ColoredPatternLayoutEncoder;
import ru.hzerr.log.policy.CancelRollingPolicy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SessionLogFactory extends LogFactory {

    private BaseFile sessionLogFile;
    private final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    private final PatternLayout consoleEncoder = new ColoredPatternLayoutEncoder();
    private final PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
    private final LayoutWrappingEncoder<ILoggingEvent> wrappingConsoleEncoder = new LayoutWrappingEncoder<>();;
    private final ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    private final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
    private final CancelRollingPolicy filePolicy = new CancelRollingPolicy();

    public SessionLogFactory() {
    }

    public void createSession() throws IOException, ConfigurationException {
        log = lc.getLogger("HElias");
        String formattedTime = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
        sessionLogFile = HElias.getProperties().getLogDir().createSubFile(formattedTime + ".log");
        if (sessionLogFile.notExists()) throw new ConfigurationException("Unable to create a log file of the current session");
        consoleEncoder.setContext(lc);
        consoleEncoder.setPattern("[%thread] %highlight(%-5level) - %highlight(%msg) %n");
        consoleEncoder.start();

        consoleAppender.setContext(lc);
        consoleAppender.setName("console");
        wrappingConsoleEncoder.setLayout(consoleEncoder);
        consoleAppender.setEncoder(wrappingConsoleEncoder);
        consoleAppender.start();

        fileEncoder.setContext(lc);
        fileEncoder.setPattern("%-12date{YYYY-MM-dd HH:mm:ss.SSS} %-5level - %msg%n");
        fileEncoder.start();

        fileAppender.setContext(lc);
        fileAppender.setEncoder(fileEncoder);
        fileAppender.setAppend(true);
        fileAppender.setFile(sessionLogFile.getLocation());

        filePolicy.setContext(lc);
        filePolicy.setParent(fileAppender);
        filePolicy.setFileNamePattern("%d{yyyy-MM-dd HH-mm-ss}.log");
        filePolicy.start();

        fileAppender.setRollingPolicy(filePolicy);
        fileAppender.start();

        log.setAdditive(false);
        log.setLevel(Level.DEBUG);
        log.addAppender(consoleAppender);
        log.addAppender(fileAppender);
    }

    @Override
    public void close() throws IOException {
        filePolicy.stop();
        fileEncoder.stop();
        fileAppender.stop();
        consoleEncoder.stop();
        wrappingConsoleEncoder.stop();
        consoleAppender.stop();
        lc.stop();
        if (sessionLogFile.sizeOf(SizeType.BYTE) == 0D) {
            sessionLogFile.delete();
        }
    }
}
