package ru.hzerr.modification.util.transform.classes;


import ru.hzerr.HElias;
import ru.hzerr.bytecode.ByteCodeBuilder;
import ru.hzerr.bytecode.ByteCodeBuilderFactory;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.modification.util.transform.SashokClass;

public class IOHelper extends SashokClass {

    @Override
    public ByteCodeBuilder transform(Profile profile) {
        return ByteCodeBuilderFactory.createMethodByteCodeBuilder(HElias.getProperties().getIOHelperClassName())
                .filterByNames("getResourceURL")
                .addCode("return " + HElias.getProperties().getLauncherClassName() + ".class.getResource('/' + $1);")
                .insertBody();
    }
}
