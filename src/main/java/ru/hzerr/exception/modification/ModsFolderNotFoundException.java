package ru.hzerr.exception.modification;

import java.io.FileNotFoundException;

public class ModsFolderNotFoundException extends FileNotFoundException {

    public ModsFolderNotFoundException() {
        super();
    }
    public ModsFolderNotFoundException(String s) {
        super(s);
    }
}
