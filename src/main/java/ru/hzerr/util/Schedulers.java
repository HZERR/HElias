package ru.hzerr.util;

import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public final class Schedulers {

    private static final HList<ScheduledExecutorService> services = new ArrayHList<>();

    public static void shutdown() { services.forEach(ExecutorService::shutdownNow); }
    public static void register(ScheduledExecutorService service) { services.add(service); }
}
