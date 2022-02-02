package ru.hzerr.loaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.io.IOUtils;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.modification.ImageNotFoundException;

import java.io.InputStream;

public class ImageLoader {

    private static final HMap<String, Image> CACHE = new HashHMap<>();

    private ImageLoader() {
    }

    public static ImageView loadImageView(String name, String extension) throws ImageNotFoundException {
        return loadImageView(name + extension);
    }

    public static ImageView loadImageView(String imageFullName) throws ImageNotFoundException {
        return loadImageView(imageFullName, 0D, 0D);
    }

    public static ImageView loadImageView(String imageFullName, double width, double height) throws ImageNotFoundException {
        return loadImageView(imageFullName, width, height, false, false);
    }

    public static
    ImageView loadImageView(String imageFullName, double width, double height, boolean preserveRatio) throws ImageNotFoundException {
        return loadImageView(imageFullName, width, height, preserveRatio, false);
    }

    public static
    ImageView loadImageView(String imageFullName, double width, double height, boolean preserveRatio, boolean smooth) throws ImageNotFoundException {
        ImageView view = new ImageView(loadImage(imageFullName, width, height, preserveRatio, smooth));
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setPreserveRatio(preserveRatio);
        view.setSmooth(smooth);
        return view;
    }

    public static Image loadImage(String imageName, String extension) throws ImageNotFoundException {
        return loadImage(imageName + extension);
    }

    public static Image loadImage(String imageFullName) throws ImageNotFoundException {
        return loadImage(imageFullName, 0);
    }

    // size - weight and height
    public static Image loadImage(String imageFullName, double size) throws ImageNotFoundException {
        return loadImage(imageFullName, size, size, false);
    }

    public static Image loadImage(String imageFullName, double width, double height) throws ImageNotFoundException {
        return loadImage(imageFullName, width, height, false);
    }

    public static Image loadImage(String imageFullName, double width, double height, boolean preserveRatio) throws ImageNotFoundException {
        return loadImage(imageFullName, width, height, preserveRatio, false);
    }

    public static Image loadImage(String imageFullName, double width, double height, boolean preserveRatio, boolean smooth) throws ImageNotFoundException {
        if (CACHE.noContainsKey(imageFullName)) {
            InputStream inputStream = ImageLoader.class.getResourceAsStream("/runtime/images/" + imageFullName);
            if (inputStream == null) throw new ImageNotFoundException("Image " + imageFullName + " was not found");
            Image image = new Image(inputStream, width, height, preserveRatio, smooth);
            IOUtils.closeQuietly(inputStream, ErrorSupport::showErrorPopup);
            return CACHE.putAndGet(imageFullName, image);
        } else return CACHE.get(imageFullName);
    }
}
