package ru.hzerr.util;

import ru.hzerr.exception.ErrorSupport;

public class LoggableThread extends Thread {

    public LoggableThread(Runnable runnable) {
        super(runnable.asJavaRunnable());
    }

    public LoggableThread(ERunnable runnable) {
        super(runnable.asJavaRunnable());
    }

    public static LoggableThread createLoggableThread(Runnable runnable) {
        return new LoggableThread(runnable);
    }

    public static LoggableThread createLoggableThread(ERunnable runnable) {
        return new LoggableThread(runnable);
    }

    public interface Runnable {

        void run() throws Throwable;

        default java.lang.Runnable asJavaRunnable() {
            return () -> {
                try {
                    run();
                } catch (Throwable th) { ErrorSupport.showErrorPopup(th); }
            };
        }
    }

    public interface ERunnable {

        void run() throws Throwable;
        void onError();

        default java.lang.Runnable asJavaRunnable() {
            return () -> {
                try {
                    run();
                } catch (Throwable th) {
                    onError();
                    ErrorSupport.showErrorPopup(th);
                }
            };
        }
    }
}
