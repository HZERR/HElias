package ru.hzerr.modification.util.transform.classes;

import ru.hzerr.HElias;
import ru.hzerr.bytecode.ByteCodeBuilder;
import ru.hzerr.bytecode.ByteCodeBuilderFactory;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.util.transform.SashokClass;

public class VerifyHelper extends SashokClass {

    @Override
    public ByteCodeBuilder transform(Project project) {
        return ByteCodeBuilderFactory.createMethodByteCodeBuilder(HElias.getProperties().getVerifyHelperClassName())
                .filterByNames("verify")
                .addCode("return $1;")
                .insertBody();
    }
}
