package ru.hzerr.exception;

import java.io.FileNotFoundException;

public class NoDefineProfileException extends FileNotFoundException {

    public NoDefineProfileException(String s) {
        super(s);
    }
}
