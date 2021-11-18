package ru.hzerr.modification.util.transform;

import ru.hzerr.bytecode.ByteCodeBuilder;
import ru.hzerr.modification.Project;

public abstract class SashokClass {

    public SashokClass() {}

    public abstract ByteCodeBuilder transform(Project project);

    public void doTransform(Project project) {
        transform(project).writeFile(project.getUnpack().getLocation());
    }
}
