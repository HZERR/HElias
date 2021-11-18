package ru.hzerr.exception.modification;

import java.io.FileNotFoundException;

public class ManifestNotFoundException extends FileNotFoundException {

    public ManifestNotFoundException() {
        super();
    }
    public ManifestNotFoundException(String s) {
        super(s);
    }
}
