package ru.hzerr.modification.util.transform.classes;

import ru.hzerr.HElias;
import ru.hzerr.bytecode.ByteCodeBuilder;
import ru.hzerr.bytecode.ByteCodeBuilderFactory;
import ru.hzerr.bytecode.MethodByteCodeBuilder;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.util.transform.SashokClass;

public class LogHelper extends SashokClass {

    @Override
    public ByteCodeBuilder transform(Project project) {
        MethodByteCodeBuilder b = ByteCodeBuilderFactory
                .createMethodByteCodeBuilder(HElias.getProperties().getLogHelperClassName())
                .filterByNames("isDebugEnabled");
        if (HElias.getProperties().isDebugForceEnabled()) b.setBodyReturnTrue();
        return b.concatMethodByteCodeBuilder()
                .filterByNames("printVersion")
                .addCode("println(\"" + HElias.getProperties().getLauncherName() +
                        " \" + $1 + \" v" + HElias.getProperties().getLauncherVersion() +
                        " (build #" + HElias.getProperties().getLauncherBuild() + ")\");")
                .insertBody();
    }
}