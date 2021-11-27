package ru.hzerr.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Fx {

    private static Scene scene;

    public static void setScene(Scene scene) { Fx.scene = scene; }
    public static Scene getScene() { return Fx.scene; }

    // Сохраняет сцену. Устанавливает и показывает ее на Stage
    public static void setSceneAndShow(Scene scene, Stage stage) {
        Fx.scene = scene;
        stage.setScene(scene);
        stage.show();
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int originalWidth = imgSize.width;
        int originalHeight = imgSize.height;
        int boundWidth = boundary.width;
        int boundHeight = boundary.height;
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > boundWidth) {
            //scale width to fit
            newWidth = boundWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > boundHeight) {
            //scale height to fit instead
            newHeight = boundHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        return new Dimension(newWidth, newHeight);
    }

    // rewrite
    @Deprecated
    public static class TimeLineBuilder {

        private KeyFrame[] keyFrames;
        private EventHandler<ActionEvent> onFinished;

        private TimeLineBuilder() {
        }

        public TimeLineBuilder onFinished(EventHandler<ActionEvent> onFinished) {
            this.onFinished = onFinished;
            return this;
        }

        public TimeLineBuilder keyFrame(KeyFrame... keyFrames) {
            this.keyFrames = keyFrames;
            return this;
        }

        public void play() {
            Timeline tmp = new Timeline(keyFrames);
            tmp.setOnFinished(onFinished);
            tmp.play();
        }

        public static TimeLineBuilder newBuilder() { return new TimeLineBuilder(); }
    }

    public interface FXMultiRunnable extends Runnable {

        default FXMultiRunnable concat(FXMultiRunnable runnable) {
            return () -> Platform.runLater(() -> {
                run();
                runnable.run();
            });
        }

        default Runnable concat(Runnable runnable) {
            return () -> Platform.runLater(() -> {
                run();
                runnable.run();
            });
        }
    }

    // TODO: 28.11.2021 SWITCH TO TIMELINE
    public static class DropShadowBackgroundEffect implements Runnable {

        private final ScheduledExecutorService DROP_SHADOW_SERVICE = Executors.newSingleThreadScheduledExecutor();
        private final DropShadow dropShadowEffect = new DropShadow();
        private final Label node;
        private int hue = 0;
        private final double saturation = 0.40D;
        private final double brightness = 0.75D;
        private boolean decrement;

        private final int MAX_VALUE = 360;
        private final int MIN_VALUE = 0;

        public DropShadowBackgroundEffect(Label node) {
            this.node = node;
            dropShadowEffect.setBlurType(BlurType.THREE_PASS_BOX);
            dropShadowEffect.setWidth(9D);
            dropShadowEffect.setHeight(12D);
            dropShadowEffect.setRadius(5D);
            dropShadowEffect.setSpread(0.85D);
        }

        @Override
        public void run() {
            DROP_SHADOW_SERVICE.scheduleAtFixedRate(() -> {
                dropShadowEffect.setColor(newColor());
                Platform.runLater(() -> node.setEffect(dropShadowEffect));
            }, 0L, 25L, TimeUnit.MILLISECONDS);
            Schedulers.register(DROP_SHADOW_SERVICE);
        }

        private Color newColor() {
            if (hue + 1 > MAX_VALUE) decrement = true;
            if (hue - 1 < MIN_VALUE) decrement = false;
            hue = decrement ? hue - 1 : hue + 1;
            return Color.hsb(hue, saturation, brightness);
        }

        public static DropShadowBackgroundEffect create(Label node) {
            return new DropShadowBackgroundEffect(node);
        }
    }
}
