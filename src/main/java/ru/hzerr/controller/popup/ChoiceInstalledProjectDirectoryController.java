package ru.hzerr.controller.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXSpinner;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.util.Fx;
import ru.hzerr.util.SystemInfo;

public class ChoiceInstalledProjectDirectoryController implements Showable {

    @FXML private AnchorPane root;
    @FXML private JFXButton apply;
    @FXML private JFXSpinner stateSpinner;
    @FXML private Label description;
    @FXML private JFXComboBox<JFXButton> testCombo;

    private final JFXPopup popup = new JFXPopup();

    public void initialize() {
        JFXButton serverHiTech = new JFXButton("HiTech");
        serverHiTech.setOnAction(event -> System.out.println("HiTech!!!"));
        JFXButton serverHiTech2 = new JFXButton("HiTech 2");
        serverHiTech2.setOnAction(event -> System.out.println("HiTech 2!!!"));
        testCombo.getItems().add(serverHiTech);
        testCombo.getItems().add(serverHiTech2);
        popup.setPopupContent(root);
    }

    @Override
    public void show() {
        int initOffsetX = SystemInfo.isWindows() ? 175 : 75;
        int initOffsetY = SystemInfo.isWindows() ? 125 : 25;
        popup.show(Fx.getScene().getRoot(), JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }
}
