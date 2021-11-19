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

import javax.swing.*;
import java.io.IOException;

/**
 * TODO: 19.11.2021 CHECK offsetX and offsetY in the popups
 */
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
    public void start(Stage stage) throws IOException {
        try {
            final Parent ROOT = FXMLLoader.getParent("main", FXMLLoader.FXMLType.ROOT, PROPERTIES.getLanguage());
            stage.setTitle("HElias");
            stage.getIcons().add(ImageLoader.loadImage("logo.png", 32D, 32D));
            stage.setResizable(false);
            stage.centerOnScreen();
            final Scene scene = new Scene(ROOT);
            ThemeLoader.load(PROPERTIES.getTheme()).applyTheme(scene);
            Fx.setSceneAndShow(scene, stage);
        } catch (Exception e) {
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            int result = JOptionPane.showConfirmDialog(frame,
                    "Message: " + e.getMessage() + "\nThe program can't seem to start correctly. Reset to factory settings?",
                    e.getClass().getSimpleName(),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            Schedulers.shutdown();
            SessionLogManager.getManager().close();
            switch (result) {
                case JOptionPane.YES_OPTION: PROPERTIES.getRootDir().delete();
                case JOptionPane.NO_OPTION: System.exit(0);
            }
        }
    }

    @Override
    public void stop() {
        Schedulers.shutdown();
        SessionLogManager.getManager().close();
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }
}
