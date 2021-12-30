package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.HDirectory;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.JFoenixAppender;

/**
 * The stage of adding the JFoenix library to the project
 */
public abstract class PrependJFoenixStage extends BaseStage<Void> {

    public PrependJFoenixStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        JFoenixAppender appender = JFoenixAppender.create();
        appender.setTempFolder(HDirectory.createTempDirectory("jfoenix"));
        appender.apply(profile.getStructureProperty().getValue().getBuildFile());
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred when adding the JFoenix library to the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.ADD_DELETE_LIBRARIES.getLevel();
    }

    @Override
    public String description() {
        return "The stage of adding the JFoenix library to the project";
    }
}
