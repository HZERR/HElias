package ru.hzerr.log;

import ch.qos.logback.classic.Logger;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;

/**
 * When adding new factories, you must ensure that the old factory is replaced (not forgetting to close it) by the new one
 * To close the factory manually, you need to call getFactory().close();
 */
public class LogManager {

    private static LogFactory factory;

    private LogManager() {
    }

    public static void startSessionFactory() throws ConfigurationException, IOException {
        factory = new SessionLogFactory();
        ((SessionLogFactory)factory).createSession();
    }

    public static LogFactory getFactory() {
        return factory;
    }

    public static Logger getLogger() {
        return factory.getLogger();
    }
}
