package ru.hzerr.loaders;

import java.util.ResourceBundle;

// maybe abstract?
public class Language {

    protected ResourceBundle bundle;

    protected Language(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getName() { return bundle.getLocale().getLanguage(); }
    public ResourceBundle getBundle() {
        return this.bundle;
    }
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return bundle.getLocale().getLanguage().equals(language.bundle.getLocale().getLanguage());
    }
}
