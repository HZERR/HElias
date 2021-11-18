package ru.hzerr.config.listener.content;

import com.google.common.base.Preconditions;

public interface ContentInstaller<T> {

    T installContent(Content userData);

    default void checkArguments(Content userData, String... keys) throws IllegalArgumentException {
        for (String key: keys) {
            Preconditions.checkArgument(userData.containsKey(key), "The argument \"" + key + "\" isn't found in the content");
        }
    }
}
