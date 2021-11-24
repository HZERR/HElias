package ru.hzerr.loaders.css;

import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.loaders.theme.ThemeLoader;
import ru.hzerr.log.LogManager;

import java.net.URL;

public class CssLoader {

    private static final HMap<String, String> CACHE = new HashHMap<>();
    private static final String CSS_PREFIX = "/runtime/style/theme";
    private static final String CSS_POSTFIX = ".css";

    private CssLoader() {
    }

    public static String load(ThemeLoader.ThemeType themeType, LoadType loadType, String name) {
        final String key = newKey(themeType, loadType, name);
        LogManager.getLogger().debug("Fetching css " + key);
        if (CACHE.noContainsKey(key)) {
            final URL cssURL = CssLoader.class.getResource(key);
            if (cssURL != null) {
                return CACHE.putAndGet(key, cssURL.toExternalForm());
            } else
                throw new IllegalArgumentException(key + " not found");
        } else
            return CACHE.get(key);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private static String newKey(ThemeLoader.ThemeType themeType, LoadType loadType, String name) {
        return new StringBuilder(CSS_PREFIX)
                .append('/').append(themeType.getPrefix())
                .append('/').append(loadType.getPrefix())
                .append('/').append(name)
                .append(CSS_POSTFIX).toString();
    }
}
