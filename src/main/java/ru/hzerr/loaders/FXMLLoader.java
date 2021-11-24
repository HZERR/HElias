package ru.hzerr.loaders;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import ru.hzerr.HElias;
import ru.hzerr.collections.HPair;
import ru.hzerr.collections.map.HMap;
import ru.hzerr.collections.map.HashHMap;
import ru.hzerr.controller.popup.Showable;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.loaders.css.CssLoader;
import ru.hzerr.loaders.css.LoadType;
import ru.hzerr.log.LogManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

// Загружает FXML
public class FXMLLoader {

    private static final HMap<HPair<String, Language>, Parent> CACHE = new HashHMap<>();

    private FXMLLoader() {
    }

    public static
    Parent getParent(String name, FXMLType fxmlType, Language language) throws IOException {
        return getParent(name, fxmlType, language, null, true);
    }

    public static
    Parent getParent(String name, FXMLType fxmlType, Language language, Object controller, boolean saveInCache) throws IOException {
        return getParent(name, fxmlType, language, controller, saveInCache, Parent.class);
    }

    public static <T>
    T getParent(String name, FXMLType fxmlType, Language language, Class<T> toClass) throws IOException {
        return getParent(name, fxmlType, language, null, true, toClass);
    }

    public static <T>
    T getParent(String name, FXMLType fxmlType,
                Language language, Object controller,
                boolean saveInCache, Class<T> toClass) throws IOException {
        HPair<String, Language> key = HPair.create(fxmlType.getPrefix() + "/" + name, language);
        if (CACHE.noContainsKey(innerKey -> innerKey.getKey().equals(key.getKey()) && innerKey.getValue().getName().equals(key.getValue().getName()))) {
            URL url = FXMLLoader.class.getResource(key.getKey() + ".fxml");
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
            if (Objects.nonNull(controller)) loader.setController(controller); // maybe delete check out?
            loader.setLocation(url);
            loader.setResources(language.getBundle());
            Parent parent = loader.load();
            if (saveInCache) CACHE.put(key, parent);
            LogManager.getLogger().debug(name + ".fxml has been successfully loaded");
            return (T) parent;
        } else return (T) CACHE.findValueByKey(innerKey -> innerKey.getKey().equals(key.getKey()) && innerKey.getValue().getName().equals(key.getValue().getName()));
    }

    public static <T>
    Parent loadParent(String name, FXMLType fxmlType, Language language, Object controller) throws IOException {
        String key = fxmlType.getPrefix() + "/" + name;
        URL url = FXMLLoader.class.getResource(key + ".fxml");
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        if (Objects.nonNull(controller)) loader.setController(controller);
        loader.setLocation(url);
        loader.setResources(language.getBundle());
        Parent parent = loader.load();
        LogManager.getLogger().debug(name + ".fxml has been successfully loaded");
        return parent;
    }

    public static void showSafePopup(String name, Language language, Showable controller) {
        try {
            showPopup(name, language, controller);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
    }

    public static void showSafePopup(String name, ResourceBundle resources, Showable controller) {
        try {
            showPopup(name, resources, controller);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
    }

    public static void showPopup(String name, Language language, Showable controller) throws IOException {
        URL url = FXMLLoader.class.getResource(FXMLType.POPUP.getPrefix() + "/" + name + ".fxml");
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setController(controller);
        loader.setLocation(url);
        loader.setResources(language.getBundle());
        Parent popupRoot = loader.load();
        // maybe is already load in fxml.FXMLLoader ?
        popupRoot.getStylesheets().setAll(CssLoader.load(HElias.getProperties().getTheme(), LoadType.POPUP, name));
        LogManager.getLogger().debug(name + ".fxml has been successfully loaded");
        Platform.runLater(controller::show);
    }

    public static void showPopup(String name, ResourceBundle resource, Showable controller) throws IOException {
        URL url = FXMLLoader.class.getResource(FXMLType.POPUP.getPrefix() + "/" + name + ".fxml");
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setController(controller);
        loader.setLocation(url);
        loader.setResources(resource);
        Parent popupRoot = loader.load();
        // maybe is already load in fxml.FXMLLoader ?
        popupRoot.getStylesheets().setAll(CssLoader.load(HElias.getProperties().getTheme(), LoadType.POPUP, name));
        LogManager.getLogger().debug(name + ".fxml has been successfully loaded");
        controller.show();
    }

    @SuppressWarnings("unchecked")
    public static <T> T lookupById(Parent parent, String id, Class<T> classToCast) {
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        T element = (T) children
                .filtered(node -> node.getId() != null && node.getId().equals(id))
                .get(0);
        if (element != null) {
            return element;
        }

        throw new IllegalArgumentException("Element with id: " + id + " not found");
    }

    public static Tab lookupById(JFXTabPane parent, String id) {
        Tab tab = parent.getTabs().filtered(node -> node.getId() != null && node.getId().equals(id)).get(0);
        if (tab != null) {
            LogManager.getLogger().debug("The lookup has been successfully completed. The tab with id: " + id + " was found");
            return tab;
        }

        throw new NullPointerException("Tab with id: " + id + " not found");
    }

    public enum FXMLType {
        ROOT("/runtime/fxml"),
        TAB("/runtime/fxml/tab"),
        PROJECTS("/runtime/fxml/project"),
        POPUP("/runtime/fxml/popup");

        private final String prefix;

        FXMLType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return this.prefix;
        }
    }
}
