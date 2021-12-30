package ru.hzerr.modification.util.transform;

import ru.hzerr.bytecode.ByteCodeBuilder;
import ru.hzerr.config.profile.Profile;

public abstract class SashokClass {

    public SashokClass() {}

    public abstract ByteCodeBuilder transform(Profile profile);

    public void doTransform(Profile profile) {
        transform(profile).writeFile(profile.getStructureProperty().getValue().getDecompressionDir().getLocation());
    }
}
