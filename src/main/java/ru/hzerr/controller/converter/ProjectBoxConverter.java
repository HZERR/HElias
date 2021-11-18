package ru.hzerr.controller.converter;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import ru.hzerr.HElias;
import ru.hzerr.config.listener.content.Content;
import ru.hzerr.config.listener.content.ContentInstaller;

import java.util.ResourceBundle;

public class ProjectBoxConverter extends StringConverter<JFXButton> implements ContentInstaller {

    private ChoiceBox<JFXButton> box;
    private final ResourceBundle resources = HElias.getProperties().getLanguage().getBundle();

    private ProjectBoxConverter() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public ProjectBoxConverter installContent(Content userData) {
        checkArguments(userData, "projects");
        this.box = userData.get("projects", ChoiceBox.class);
        return this;
    }

    @Override
    public String toString(JFXButton o) {
        if (o != null) return o.getText();
        else return resources.getString("tab.patcher.projects.prompt.text");
    }

    @Override
    public JFXButton fromString(String text) {
        for (JFXButton button : box.getItems()) {
            if (button.getText().equals(text)) return button;
        }

        return null;
    }

    public static ProjectBoxConverter newConverter() { return new ProjectBoxConverter(); }
}
