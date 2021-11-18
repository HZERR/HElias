package ru.hzerr.loaders;

import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.exception.modification.LanguageNotFoundException;
import ru.hzerr.stream.HStream;

import java.util.Locale;
import java.util.ResourceBundle;

// Загрузчик языковых пакетов
public class LanguageLoader {

    private static final HMap<String, Language> CACHE = new HashHMap<>();

    private LanguageLoader() {
    }

    public static Language getEnglishLanguage() { return getLanguage(LanguageType.EN); }
    public static Language getRussianLanguage() { return getLanguage(LanguageType.RU); }
    public static Language getLanguageEmpty() { return new Language(null); }

    public static Language getLanguage(LanguageType languageType) {
        if (CACHE.noContainsKey(languageType.getType())) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("runtime.bundle.language", new Locale(languageType.getType()));
            return CACHE.putAndGet(languageType.getType(), new Language(resourceBundle));
        } else return CACHE.get(languageType.getType());
    }

    public enum LanguageType {
        RU("ru"),
        EN("en");

        private final String type;

        LanguageType(String type) {
            this.type = type;
        }

        public String getType() { return this.type; }

        public static LanguageType byType(String type) {
            return HStream.of(values()).filter(innerType -> innerType.getType().equals(type)).findFirst().orElseThrow(LanguageNotFoundException::new);
        }
    }
}
