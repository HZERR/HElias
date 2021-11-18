package ru.hzerr.modification.chain.impl;

import io.reactivex.rxjava3.functions.Function;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.chain.strategy.BaseLayeredProjectChangerChain;
import ru.hzerr.modification.chain.strategy.BiConsumer;
import ru.hzerr.modification.chain.strategy.EmptyConsumer;
import ru.hzerr.modification.chain.strategy.Level;
import ru.hzerr.modification.util.*;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * 1. Очистка
 * 2. Распаковка
 * 3. Изменение манифеста/байт-кода
 * 4. Сборка
 * 5. Добавление/Удаление библиотеки
 * 6. Обновление
 * 7. Старт
 */
public class Sashok274LayeredProjectChangerChain extends BaseLayeredProjectChangerChain {

    public Sashok274LayeredProjectChangerChain(Project project) {
        super(project);
    }

    public <T>
    void addOnCleanupBuildFile(Function<? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(onAction, onFinished, level);
    }

    public <T>
    void addOnCleanupBuildFile(Function<? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(onAction, onFinished, Level.CLEANUP.getLevel());
    }

    public void addOnCleanupBuildFile(EmptyConsumer<? super Project> onAction, Runnable onFinished) {
        super.addAction(onAction::emptyAccept, o -> onFinished.run(), Level.CLEANUP.getLevel());
    }

    public <T>
    void addOnCleanupProjectFolder(Function<? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(onAction, onFinished, level);
    }

    public <T>
    void addOnCleanupProjectFolder(Function<? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(onAction, onFinished, Level.CLEANUP.getLevel());
    }

    public void addOnCleanupProjectFolder(EmptyConsumer<? super Project> onAction, Runnable onFinished) {
        super.addAction(onAction::emptyAccept, o -> onFinished.run(), Level.CLEANUP.getLevel());
    }

    public <T>
    void addOnDecompress(BiFunction<? super Decompressor, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(Decompressor.create(), project), onFinished, level);
    }

    public <T>
    void addOnDecompress(BiFunction<? super Decompressor, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(Decompressor.create(), project), onFinished, Level.DECOMPRESS.getLevel());
    }

    public <T>
    void addOnDecompress(BiConsumer<? super Decompressor, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(Decompressor.create(), project), o -> onFinished.run(), Level.DECOMPRESS.getLevel());
    }

    public <T>
    void addOnChangeManifest(BiFunction<? super ManifestChanger, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(ManifestChanger.create(), project), onFinished, level);
    }

    public <T>
    void addOnChangeManifest(BiFunction<? super ManifestChanger, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(ManifestChanger.create(), project), onFinished, Level.TRANSFORM.getLevel());
    }

    public <T>
    void addOnChangeManifest(BiConsumer<? super ManifestChanger, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(ManifestChanger.create(), project), o -> onFinished.run(), Level.TRANSFORM.getLevel());
    }

    public <T>
    void addOnTransform(BiFunction<? super Transformator, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(Transformator.create(), project), onFinished, level);
    }

    public <T>
    void addOnTransform(BiFunction<? super Transformator, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(Transformator.create(), project), onFinished, Level.TRANSFORM.getLevel());
    }

    public <T>
    void addOnTransform(BiConsumer<? super Transformator, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(Transformator.create(), project), o -> onFinished.run(), Level.TRANSFORM.getLevel());
    }

    public <T>
    void addOnBuild(BiFunction<? super Builder, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(Builder.create(), project), onFinished, level);
    }

    public <T>
    void addOnBuild(BiFunction<? super Builder, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(Builder.create(), project), onFinished, Level.BUILD.getLevel());
    }

    public <T>
    void addOnBuild(BiConsumer<? super Builder, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(Builder.create(), project), o -> onFinished.run(), Level.BUILD.getLevel());
    }

    public <T>
    void addOnRebuild(BiFunction<? super Builder, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(Builder.create(), project), onFinished, Level.BUILD.getLevel());
    }

    public <T>
    void addOnRebuild(BiConsumer<? super Builder, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(Builder.create(), project), o -> onFinished.run(), Level.BUILD.getLevel());
    }

    public <T>
    void addOnChangeBackground(Function<? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(onAction, onFinished, level);
    }

    public <T>
    void addOnChangeBackground(EmptyConsumer<? super Project> onAction, Runnable onFinished) {
        super.addAction(onAction::emptyAccept, o -> onFinished.run(), Level.CHANGE_RESOURCES.getLevel());
    }

    public <T>
    void addOnUpdateRuntimeFolder(BiFunction<? super Updater, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(Updater.create(), project), onFinished, level);
    }

    public <T>
    void addOnUpdateRuntimeFolder(BiFunction<? super Updater, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(Updater.create(), project), onFinished, Level.UPDATE.getLevel());
    }

    public <T>
    void addOnUpdateRuntimeFolder(BiConsumer<? super Updater, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(Updater.create(), project), o -> onFinished.run(), Level.UPDATE.getLevel());
    }

    public <T>
    void addOnAppendJFoenix(BiFunction<? super JFoenixAppender, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(JFoenixAppender.create(), project), onFinished, level);
    }

    public <T>
    void addOnAppendJFoenix(BiFunction<? super JFoenixAppender, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(JFoenixAppender.create(), project), onFinished, Level.ADD_DELETE_LIBRARIES.getLevel());
    }

    public <T>
    void addOnAppendJFoenix(BiConsumer<? super JFoenixAppender, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(JFoenixAppender.create(), project), o -> onFinished.run(), Level.ADD_DELETE_LIBRARIES.getLevel());
    }

    public <T>
    void addOnDeleteJFoenix(BiFunction<? super JFoenixDeleter, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(JFoenixDeleter.create(), project), onFinished, level);
    }

    public <T>
    void addOnDeleteJFoenix(BiFunction<? super JFoenixDeleter, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(JFoenixDeleter.create(), project), onFinished, Level.ADD_DELETE_LIBRARIES.getLevel());
    }

    public <T>
    void addOnDeleteJFoenix(BiConsumer<? super JFoenixDeleter, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(JFoenixDeleter.create(), project), o -> onFinished.run(), Level.ADD_DELETE_LIBRARIES.getLevel());
    }

    public <T>
    void addOnStart(BiFunction<? super Launcher, ? super Project, T> onAction, Consumer<T> onFinished, Integer level) {
        super.addAction(project -> onAction.apply(Launcher.create(), project), onFinished, level);
    }

    public <T>
    void addOnStart(BiFunction<? super Launcher, ? super Project, T> onAction, Consumer<T> onFinished) {
        super.addAction(project -> onAction.apply(Launcher.create(), project), onFinished, Level.START.getLevel());
    }

    public <T>
    void addOnStart(BiConsumer<? super Launcher, ? super Project> onAction, Runnable onFinished) {
        super.addAction(project -> onAction.accept(Launcher.create(), project), o -> onFinished.run(), Level.START.getLevel());
    }
}
