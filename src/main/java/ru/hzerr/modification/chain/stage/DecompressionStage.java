package ru.hzerr.modification.chain.stage;

import ru.hzerr.config.profile.Profile;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.chain.BaseStage;
import ru.hzerr.modification.chain.Level;
import ru.hzerr.modification.util.Decompressor;

/**
 * The decompression stage of the project
 */
public abstract class DecompressionStage extends BaseStage<Void> {

    public DecompressionStage() {
        super();
    }

    @Override
    public Void run(Profile profile) throws Throwable {
        Decompressor decompressor = Decompressor.create();
        decompressor.setDecompressionFolder(profile.getStructureProperty().getValue().getDecompressionDir());
        decompressor.apply(profile.getStructureProperty().getValue().getCommercialProjectJarFile());
        return null;
    }

    @Override
    public <X extends Throwable> void onError(X throwable) {
        LogManager.getLogger().error("An error occurred when unpacking the project");
        ErrorSupport.showErrorPopup(throwable);
        LogManager.getLogger().error("Stopping further processing...");
    }

    @Override
    public Integer level() {
        return Level.DECOMPRESS.getLevel();
    }

    @Override
    public String description() {
        return "The decompression stage of the project";
    }
}
