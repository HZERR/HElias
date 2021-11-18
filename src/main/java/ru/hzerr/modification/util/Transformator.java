package ru.hzerr.modification.util;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.util.transform.SashokClasses;

import java.net.URL;
import java.net.URLClassLoader;

public class Transformator {

    private Transformator() {}

    public void apply(Project project) throws Exception {
        URL[] projectUrls = new URL[] {project.getOriginalProjectFile().asURL()};
        URLClassLoader classLoader = new URLClassLoader(projectUrls);
        LoaderClassPath loaderClassPath = new LoaderClassPath(classLoader);
        ClassPool.getDefault().appendClassPath(loaderClassPath);
        SashokClasses.doTransform(project);
        ClassPool.getDefault().removeClassPath(loaderClassPath);
        classLoader.close();
    }

    public static Transformator create() { return new Transformator(); }
}
