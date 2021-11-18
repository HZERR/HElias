package ru.hzerr;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.configuration2.ex.ConfigurationException;
import ru.hzerr.config.Properties;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.loaders.ImageLoader;
import ru.hzerr.loaders.theme.ThemeLoader;
import ru.hzerr.log.SessionLogManager;
import ru.hzerr.util.Fx;
import ru.hzerr.util.Schedulers;

import java.awt.*;
import java.io.IOException;

public class HElias extends Application {

    private static final Properties PROPERTIES = Properties.getInstance();

    @Override
    public void init() {
        try {
            PROPERTIES.init();
            SessionLogManager.getManager().getLogger().fine("Properties was initialized");
        } catch (IOException | ConfigurationException e) {
            ErrorSupport.showInternalError(e);
            System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) throws IOException, FontFormatException {
        final Parent ROOT = FXMLLoader.getParent("main", FXMLLoader.FXMLType.ROOT, PROPERTIES.getLanguage());
        stage.setTitle("HElias");
        stage.getIcons().add(ImageLoader.loadImage("logo.png", 32D, 32D));
        stage.setResizable(false);
        stage.centerOnScreen();
        final Scene scene = new Scene(ROOT);
        ThemeLoader.load(PROPERTIES.getTheme()).applyTheme(scene);
        Fx.setSceneAndShow(scene, stage);
    }

    @Override
    public void stop() {
        Schedulers.shutdown();
        SessionLogManager.getManager().close();
    }

    public static Properties getProperties() { return PROPERTIES; }
}
