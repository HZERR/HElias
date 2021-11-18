package ru.hzerr.exception.modification;

import java.io.FileNotFoundException;

public class JavaNotFoundException extends FileNotFoundException {

    public JavaNotFoundException() {
        super();
    }
    public JavaNotFoundException(String s) {
        super(s);
    }
}
