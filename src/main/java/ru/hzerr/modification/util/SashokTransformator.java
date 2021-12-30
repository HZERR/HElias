package ru.hzerr.modification.util;

import javassist.ClassPool;
import javassist.LoaderClassPath;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.modification.util.transform.SashokClasses;

import java.net.URL;
import java.net.URLClassLoader;

public class SashokTransformator {

    private SashokTransformator() {}

    public void apply(Profile profile) throws Exception {
        URL[] projectUrls = new URL[] {profile.getStructureProperty().getValue().getCommercialProjectJarFile().asURL()};
        URLClassLoader classLoader = new URLClassLoader(projectUrls);
        LoaderClassPath loaderClassPath = new LoaderClassPath(classLoader);
        ClassPool.getDefault().appendClassPath(loaderClassPath);
        SashokClasses.doTransform(profile);
        ClassPool.getDefault().removeClassPath(loaderClassPath);
        classLoader.close();
    }

    public static SashokTransformator create() { return new SashokTransformator(); }
}
