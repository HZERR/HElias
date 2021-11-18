package ru.hzerr.exception.modification;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageNotFoundException extends FileNotFoundException {

    public ImageNotFoundException() {
        super();
    }
    public ImageNotFoundException(String s) {
        super(s);
    }
}
