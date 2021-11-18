package ru.hzerr.util;

import ru.hzerr.exception.ErrorSupport;

public final class LoggableThread extends Thread {

    public LoggableThread(Runnable runnable) {
        super(runnable.asJavaRunnable());
    }

    public interface Runnable {

        void run() throws Exception;

        default java.lang.Runnable asJavaRunnable() {
            return () -> {
                try {
                    run();
                } catch (Throwable th) { ErrorSupport.showErrorPopup(th); }
            };
        }
    }
}
