package ru.hzerr.modification.util.transform;

import ru.hzerr.modification.Project;
import ru.hzerr.modification.util.transform.classes.*;
import ru.hzerr.stream.HStream;

public enum SashokClasses {

    CLIENT_LAUNCHER(new ClientLauncher()),
    SECURITY_HELPER(new SecurityHelper()),
    IO_HELPER(new IOHelper()),
    LAUNCHER(new Launcher()),
//    VERIFY_HELPER(new VerifyHelper()),
    LOG_HELPER(new LogHelper());
//    UPDATE_REQUEST(new UpdateRequest());

    private final SashokClass sashokClass;

    SashokClasses(SashokClass sashokClass) {
        this.sashokClass = sashokClass;
    }

    public static void doTransform(Project project) {
        HStream.of(values()).forEach(sashokClass -> sashokClass.getSashokClass().doTransform(project));
    }

    public SashokClass getSashokClass() { return sashokClass; }
}
