package ru.hzerr.modification.util.transform;

import ru.hzerr.config.profile.Profile;
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

    public static void doTransform(Profile profile) {
        HStream.of(values()).forEach(sashokClass -> sashokClass.getSashokClass().doTransform(profile));
    }

    public SashokClass getSashokClass() { return sashokClass; }
}
