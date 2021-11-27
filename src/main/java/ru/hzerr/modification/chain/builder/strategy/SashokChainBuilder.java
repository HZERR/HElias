package ru.hzerr.modification.chain.builder.strategy;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.config.listener.content.ContentInstaller;
import ru.hzerr.exception.modification.ManifestNotFoundException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.HDirectory;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.chain.impl.Sashok274LayeredProjectChangerChain;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.modification.util.Transformator;

// TODO: 28.11.2021 SWITCH TO PROFILES. MAY BE DELETE THE CONTENT INSTALLER?
public abstract class SashokChainBuilder extends Sashok274LayeredProjectChangerChain implements ContentInstaller, Initializable {

    public SashokChainBuilder(Project project) {
        super(project);
    }

    public abstract void onSuccessBuildFileDeletion(); // () -> state.setText(resources.getString("tab.patcher.clear.build.file.on.success.action.text"))
    public abstract void onSuccessProjectFolderDeletion(); // () -> state.setText(resources.getString("tab.patcher.clear.project.folder.on.success.action.text"))
    public abstract void onSuccessDecompression(); // () -> state.setText(resources.getString("tab.patcher.unpack.on.success.action.text"))
    public abstract void onSuccessChangeManifest(); // () -> state.setText(resources.getString("tab.patcher.change.manifest.on.success.action.text"))
    public abstract void onSuccessTransformation(); // () -> state.setText(resources.getString("tab.patcher.change.bytecode.on.success.action.text"))
    public abstract void onSuccessChangeBackground(); // () -> ???
    public abstract void onSuccessBuild(); // () -> state.setText(resources.getString("tab.patcher.build.on.success.action.text")));
    public abstract void onSuccessRebuild();
    public abstract void onSuccessPrependJFoenixLibrary(); // () -> state.setText(resources.getString("tab.patcher.add.jfoenix.on.success.action.text"))
    public abstract void onSuccessDeleteJFoenixLibrary(); // () -> state.setText(resources.getString("tab.patcher.delete.jfoenix.on.success.action.text"))
    public abstract void onSuccessUpdateRuntimeFolder(); // () -> state.setText(resources.getString("tab.patcher.update.runtime.folder.on.success.action.text"))

    public abstract void onChangeBackground();

    public void onCleanupBuildFile() { addOnCleanupBuildFile(Project::deleteBuildFileIfNeeded, this::onSuccessBuildFileDeletion); }
    public void onCleanupProjectFolder() { addOnCleanupProjectFolder(Project::cleanBuildFiles, this::onSuccessProjectFolderDeletion); }
    public void onDecompress() {
        addOnDecompress((decompressor, innerProject) -> {
            decompressor.setDecompressionFolder(innerProject.getUnpack());
            decompressor.apply(innerProject.getOriginalProjectFile());
        }, this::onSuccessDecompression);
    }
    public void onDeleteSecurity() {
        addOnChangeManifest((manifestChanger, innerProject) -> {
            final BaseDirectory META_INF = innerProject.getUnpack()
                    .findDirectoriesByNames("META-INF")
                    .findFirst().orElseThrow(ManifestNotFoundException::new);
            manifestChanger.apply(META_INF);
        }, this::onSuccessChangeManifest);

        addOnTransform(Transformator::apply, this::onSuccessTransformation);
    }
    public void onBuild() {
        addOnBuild((builder, innerProject) -> {
            builder.setNewJarFile(innerProject.getBuild());
            builder.apply(innerProject.getUnpack().getAllFiles(false));
        }, this::onSuccessBuild);
    }
    public void onRebuild() {
        addOnRebuild((builder, innerProject) -> {
            if (innerProject.getBuild().exists()) innerProject.deleteBuildFile();
            builder.setNewJarFile(innerProject.getBuild());
            builder.apply(innerProject.getUnpack().getAllFiles(false));
        }, this::onSuccessRebuild);
    }
    public void onPrependJFoenix() {
        addOnAppendJFoenix((appender, innerProject) -> {
            appender.setTempFolder(HDirectory.createTempDirectory("jfoenix"));
            appender.apply(innerProject.getBuild());
        }, this::onSuccessPrependJFoenixLibrary);
    }
    public void onDeleteJFoenix() {
        addOnDeleteJFoenix((deleter, innerProject) -> {
            deleter.apply(innerProject.getBuild());
        }, this::onSuccessDeleteJFoenixLibrary);
    }
    public void onUpdateRuntimeFolder() {
        addOnUpdateRuntimeFolder((updater, innerProject) -> {
            updater.setFilesToBeUpdated("runtime");
            updater.setFolderWithFiles(innerProject.getUnpack());
            updater.apply(innerProject.getBuild());
        }, this::onSuccessUpdateRuntimeFolder);
    }

    public abstract <T extends State> void init(@NotNull T state);

//    private void debugState(@NotNull Sashok274SettingState currentState) {
//        SessionLogManager.getManager().getLogger().info("Print debug setting state:");
//        SessionLogManager.getManager().getLogger().info("\tDelete build file: " + currentState.shouldDeleteBuildFile());
//        SessionLogManager.getManager().getLogger().info("\tDelete project folder: " + currentState.shouldDeleteProjectFolder());
//        SessionLogManager.getManager().getLogger().info("\tDecompress: " + currentState.shouldUnpack());
//        SessionLogManager.getManager().getLogger().info("\tDelete security: " + currentState.isDeleteSecurity());
//        SessionLogManager.getManager().getLogger().info("\tChange background enabled: " + currentState.isChangeBackgroundEnabled());
//        SessionLogManager.getManager().getLogger().info("\tRebuild: " + currentState.shouldRebuild());
//        SessionLogManager.getManager().getLogger().info("\tBuild: " + currentState.shouldBuild());
//        SessionLogManager.getManager().getLogger().info("\tAdd JFoenix: " + currentState.isAddJFoenix());
//        SessionLogManager.getManager().getLogger().info("\tDelete JFoenix: " + currentState.isDeleteJFoenix());
//        SessionLogManager.getManager().getLogger().info("\tUpdate Runtime folder: " + currentState.shouldUpdateRuntimeFolder());
//    }
}
