package ru.hzerr.modification.chain.advanced;

import com.jfoenix.controls.JFXSpinner;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface VBoxModificationEditable {

    default void append(VBox infoBox, String processMessage) {
        final HBox horizontalBox = new HBox();
        horizontalBox.setPrefHeight(40);
        final Label processInfo = new Label(processMessage);
        processInfo.setPrefHeight(40);
        horizontalBox.getChildren().addAll(processInfo);
        infoBox.getChildren().add(horizontalBox);
    }

    default void switchStateToProcessing(VBox vBox) {
        HBox target = ((HBox) vBox.getChildren().get(vBox.getChildren().size() - 1));
        if (target.getChildren().size() > 1) {
            target.getChildren().remove(1);
        }
        JFXSpinner processSpinner = new JFXSpinner(-1.0D);
        processSpinner.setPrefHeight(40);
        target.getChildren().add(processSpinner);
    }

    default void switchStateToCompleted(VBox vBox) {
        HBox target = ((HBox) vBox.getChildren().get(vBox.getChildren().size() - 1));
        if (target.getChildren().size() > 1) {
            target.getChildren().remove(1);
        }
        final Label completedText = new Label("YES");
        completedText.setPrefHeight(40);
        completedText.getStyleClass().add("state-yes");
        target.getChildren().add(completedText);
    }

    default void switchStateToIncorrect(VBox vBox) {
        HBox target = ((HBox) vBox.getChildren().get(vBox.getChildren().size() - 1));
        if (target.getChildren().size() > 1) {
            target.getChildren().remove(1);
        }
        final Label incorrectText = new Label("NO");
        incorrectText.setPrefHeight(40);
        incorrectText.getStyleClass().add("state-no");
        target.getChildren().add(incorrectText);
    }
}
