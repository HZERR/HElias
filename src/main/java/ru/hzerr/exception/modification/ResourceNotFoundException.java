package ru.hzerr.exception.modification;

import java.io.FileNotFoundException;

public class ResourceNotFoundException extends FileNotFoundException {

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
