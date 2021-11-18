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

    public static Theme loadDarkTheme() {
        if (CACHE.noContainsKey("dark")) {
            String root = CssLoader.load(ThemeLoader.ThemeType.DARK, LoadType.ROOT, "main");
            String patcher = CssLoader.load(ThemeType.DARK, LoadType.TAB, "patcher");
            String projects = CssLoader.load(ThemeType.DARK, LoadType.TAB, "projects");
            String modWizard = CssLoader.load(ThemeType.DARK, LoadType.TAB, "modWizard");
            String settings = CssLoader.load(ThemeType.DARK, LoadType.TAB, "settings");
            Dark dark = new Dark();
            dark.addStylesheet(Entity.create("root", root));
            dark.addStylesheet(Entity.create("patcher", patcher));
            dark.addStylesheet(Entity.create("projects", projects));
            dark.addStylesheet(Entity.create("modWizard", modWizard));
            dark.addStylesheet(Entity.create("settings", settings));
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
            String settings = CssLoader.load(ThemeType.PURPLE_V1, LoadType.TAB, "settings");
            Purple purple = new Purple();
            purple.addStylesheet(Entity.create("root", root));
            purple.addStylesheet(Entity.create("patcher", patcher));
            purple.addStylesheet(Entity.create("projects", projects));
            purple.addStylesheet(Entity.create("modWizard", modWizard));
            purple.addStylesheet(Entity.create("settings", settings));
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
            String settings = CssLoader.load(ThemeType.PURPLE_V2, LoadType.TAB, "settings");
            Purple purple = new Purple();
            purple.addStylesheet(Entity.create("root", root));
            purple.addStylesheet(Entity.create("patcher", patcher));
            purple.addStylesheet(Entity.create("projects", projects));
            purple.addStylesheet(Entity.create("modWizard", modWizard));
            purple.addStylesheet(Entity.create("settings", settings));
            return CACHE.putAndGet("purple_v2", purple);
        } else
            return CACHE.get("purple_v2");
    }

    public enum ThemeType {
        DARK("dark"),
        PURPLE_V1("purple/v1"),
        PURPLE_V2("purple/v2");

        private final String prefix;

        ThemeType(String prefix) { this.prefix = prefix; }

        public String getPrefix() { return this.prefix; }
    }
}
