package ru.hzerr.loaders.theme;

import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.loaders.css.CssLoader;
import ru.hzerr.loaders.css.LoadType;

public class ThemeLoader {

    private static final HMap<String, Theme> CACHE = new HashHMap<>();

    private ThemeLoader() {
    }

    public static Theme load(ThemeType type) {
        switch (type) {
            case DARK: return loadDarkTheme();
            case PURPLE_V1: return loadPurpleThemeV1();
            case PURPLE_V2: return loadPurpleThemeV2();
            default: throw new IllegalArgumentException("ThemeType " + type.name() + " can't be loaded");
        }
    }

    public static Theme loadDragonTheme() {
        if (CACHE.noContainsKey("dragon")) {
            String root = CssLoader.load(ThemeType.DRAGON, LoadType.ROOT, "main");
            String patcher = CssLoader.load(ThemeType.DRAGON, LoadType.TAB, "patcher");
            String projects = CssLoader.load(ThemeType.DRAGON, LoadType.TAB, "projects");
            String modWizard = CssLoader.load(ThemeType.DRAGON, LoadType.TAB, "modWizard");
            String profiles = CssLoader.load(ThemeType.DRAGON, LoadType.TAB, "profiles");
            String settings = CssLoader.load(ThemeType.DRAGON, LoadType.TAB, "settings");
            Theme dragon = new Dragon();
            dragon.addStylesheet(Stylesheet.create("root", root));
            dragon.addStylesheet(Stylesheet.create("patcher", patcher));
            dragon.addStylesheet(Stylesheet.create("projects", projects));
            dragon.addStylesheet(Stylesheet.create("modWizard", modWizard));
            dragon.addStylesheet(Stylesheet.create("profiles", profiles));
            dragon.addStylesheet(Stylesheet.create("settings", settings));
            return CACHE.putAndGet("dragon", dragon);
        } else
            return CACHE.get("dragon");
    }

    public static Theme loadDarkTheme() {
        if (CACHE.noContainsKey("dark")) {
            String root = CssLoader.load(ThemeType.DARK, LoadType.ROOT, "main");
            String patcher = CssLoader.load(ThemeType.DARK, LoadType.TAB, "patcher");
            String projects = CssLoader.load(ThemeType.DARK, LoadType.TAB, "projects");
            String modWizard = CssLoader.load(ThemeType.DARK, LoadType.TAB, "modWizard");
            String profiles = CssLoader.load(ThemeType.DARK, LoadType.TAB, "profile");
            String settings = CssLoader.load(ThemeType.DARK, LoadType.TAB, "settings");
            Theme dark = new Dark();
            dark.addStylesheet(Stylesheet.create("root", root));
            dark.addStylesheet(Stylesheet.create("patcher", patcher));
            dark.addStylesheet(Stylesheet.create("projects", projects));
            dark.addStylesheet(Stylesheet.create("modWizard", modWizard));
            dark.addStylesheet(Stylesheet.create("profiles", profiles));
            dark.addStylesheet(Stylesheet.create("settings", settings));
            return CACHE.putAndGet("dark", dark);
        } else
            return CACHE.get("dark");
    }

    public static Theme loadPurpleThemeV1() {
        if (CACHE.noContainsKey("purple_v1")) {
            String root = CssLoader.load(ThemeLoader.ThemeType.PURPLE_V1, LoadType.ROOT, "main");
            String patcher = CssLoader.load(ThemeType.PURPLE_V1, LoadType.TAB, "patcher");
            String projects = CssLoader.load(ThemeType.PURPLE_V1, LoadType.TAB, "projects");
            String modWizard = CssLoader.load(ThemeType.PURPLE_V1, LoadType.TAB, "modWizard");
            String profiles = CssLoader.load(ThemeType.PURPLE_V1, LoadType.TAB, "profile");
            String settings = CssLoader.load(ThemeType.PURPLE_V1, LoadType.TAB, "settings");
            Theme purple = new Purple();
            purple.addStylesheet(Stylesheet.create("root", root));
            purple.addStylesheet(Stylesheet.create("patcher", patcher));
            purple.addStylesheet(Stylesheet.create("projects", projects));
            purple.addStylesheet(Stylesheet.create("modWizard", modWizard));
            purple.addStylesheet(Stylesheet.create("profiles", profiles));
            purple.addStylesheet(Stylesheet.create("settings", settings));
            return CACHE.putAndGet("purple_v1", purple);
        } else
            return CACHE.get("purple_v1");
    }

    public static Theme loadPurpleThemeV2() {
        if (CACHE.noContainsKey("purple_v2")) {
            String root = CssLoader.load(ThemeLoader.ThemeType.PURPLE_V2, LoadType.ROOT, "main");
            String patcher = CssLoader.load(ThemeType.PURPLE_V2, LoadType.TAB, "patcher");
            String projects = CssLoader.load(ThemeType.PURPLE_V2, LoadType.TAB, "projects");
            String modWizard = CssLoader.load(ThemeType.PURPLE_V2, LoadType.TAB, "modWizard");
            String profiles = CssLoader.load(ThemeType.PURPLE_V2, LoadType.TAB, "profile");
            String settings = CssLoader.load(ThemeType.PURPLE_V2, LoadType.TAB, "settings");
            Theme purple = new Purple();
            purple.addStylesheet(Stylesheet.create("root", root));
            purple.addStylesheet(Stylesheet.create("patcher", patcher));
            purple.addStylesheet(Stylesheet.create("projects", projects));
            purple.addStylesheet(Stylesheet.create("modWizard", modWizard));
            purple.addStylesheet(Stylesheet.create("profiles", profiles));
            purple.addStylesheet(Stylesheet.create("settings", settings));
            return CACHE.putAndGet("purple_v2", purple);
        } else
            return CACHE.get("purple_v2");
    }

    public enum ThemeType {
        DRAGON("dragon"),
        DARK("dark"),
        PURPLE_V1("purple/v1"),
        PURPLE_V2("purple/v2");

        private final String prefix;

        ThemeType(String prefix) { this.prefix = prefix; }

        public String getPrefix() { return this.prefix; }
    }
}
