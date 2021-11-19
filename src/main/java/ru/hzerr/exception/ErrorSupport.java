package ru.hzerr.exception;

import ru.hzerr.controller.popup.ErrorController;
import ru.hzerr.controller.popup.WarningController;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.loaders.LanguageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public final class ErrorSupport {

    public static void showWarningPopup(String title, String message) {
        WarningController controller = new WarningController();
        controller.setTitle(title);
        controller.setMessage(message);
        try {
            FXMLLoader.showPopup("warning", LanguageLoader.getLanguageEmpty(), controller);
        } catch (IOException io) { showInternalError(io); }
    }

    public static void showErrorPopup(Throwable throwable) {
        ErrorController controller = new ErrorController();
        controller.setException(throwable);
        try {
            FXMLLoader.showPopup("error", LanguageLoader.getLanguageEmpty(), controller);
        } catch (IOException io) { showInternalError(io); }
    }

    public static void showInternalError(String message) {
        JOptionPane.showMessageDialog(new Frame(), message, "HElias Internal Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInternalError(Throwable e) {
        //noinspection StringBufferReplaceableByString
        StringBuilder message = new StringBuilder();
        message.append("Произошла непредвиденная ошибка. Пожалуйста, сообщите об этом разработчику\n");
        message.append("Class: ").append(e.getClass().getSimpleName()).append('\n');
        message.append("Message: ").append(e.getMessage()).append('\n');
        message.append(e.getStackTrace()[0]).append('\n');
        message.append(e.getStackTrace()[1]).append('\n');
        message.append(e.getStackTrace()[2]).append('\n');
        message.append(e.getStackTrace()[3]).append('\n');
        JOptionPane.showMessageDialog(new Frame(), message.toString(), "HElias Internal Error", JOptionPane.ERROR_MESSAGE);
    }
}
