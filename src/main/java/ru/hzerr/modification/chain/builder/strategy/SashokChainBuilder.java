//package ru.hzerr.modification.chain.builder.strategy;
//
//import org.jetbrains.annotations.NotNull;
//import ru.hzerr.config.listener.content.ContentInstaller;
//import ru.hzerr.config.profile.Profile;
//import ru.hzerr.exception.modification.ManifestNotFoundException;
//import ru.hzerr.file.BaseDirectory;
//import ru.hzerr.file.HDirectory;
//import ru.hzerr.modification.chain.impl.Sashok274LayeredProjectChangerChain;
//import ru.hzerr.modification.state.strategy.State;
//import ru.hzerr.modification.util.SashokTransformator;
//
//// TODO: 28.11.2021 SWITCH TO PROFILES. MAY BE DELETE THE CONTENT INSTALLER?
//@Deprecated
//public abstract class SashokChainBuilder extends Sashok274LayeredProjectChangerChain implements ContentInstaller, Initializable {
//
//    public SashokChainBuilder(Profile profile) {
//        super(profile);
//    }
//
//    public abstract void onSuccessBuildFileDeletion(); // () -> state.setText(resources.getString("tab.patcher.clear.build.file.on.success.action.text"))
//    public abstract void onSuccessProjectFolderDeletion(); // () -> state.setText(resources.getString("tab.patcher.clear.project.folder.on.success.action.text"))
//    public abstract void onSuccessDecompression(); // () -> state.setText(resources.getString("tab.patcher.unpack.on.success.action.text"))
//    public abstract void onSuccessChangeManifest(); // () -> state.setText(resources.getString("tab.patcher.change.manifest.on.success.action.text"))
//    public abstract void onSuccessTransformation(); // () -> state.setText(resources.getString("tab.patcher.change.bytecode.on.success.action.text"))
//    public abstract void onSuccessChangeBackground(); // () -> ???
//    public abstract void onSuccessBuild(); // () -> state.setText(resources.getString("tab.patcher.build.on.success.action.text")));
//    public abstract void onSuccessRebuild();
//    public abstract void onSuccessPrependJFoenixLibrary(); // () -> state.setText(resources.getString("tab.patcher.add.jfoenix.on.success.action.text"))
//    public abstract void onSuccessDeleteJFoenixLibrary(); // () -> state.setText(resources.getString("tab.patcher.delete.jfoenix.on.success.action.text"))
//    public abstract void onSuccessUpdateRuntimeFolder(); // () -> state.setText(resources.getString("tab.patcher.update.runtime.folder.on.success.action.text"))
//
//    public abstract void onChangeBackground();
//
//    public void onCleanupBuildFile() {
//        addOnCleanupBuildFile(profile -> profile.getStructureProperty().getValue().deleteBuildFile(), this::onSuccessBuildFileDeletion);
//    }
//    public void onCleanupProjectFolder() {
//        addOnCleanupProjectFolder(profile -> {
//            profile.getStructureProperty().getValue().cleanupDecompressDirectory();
//            profile.getStructureProperty().getValue().deleteBuildFile();
//        }, this::onSuccessProjectFolderDeletion);
//    }
//    public void onDecompress() {
//        addOnDecompress((decompressor, profile) -> {
//            decompressor.setDecompressionFolder(profile.getStructureProperty().getValue().getDecompressionDir());
//            decompressor.apply(profile.getStructureProperty().getValue().getCommercialProjectJarFile());
//        }, this::onSuccessDecompression);
//    }
//    public void onDeleteSecurity() {
//        addOnChangeManifest((manifestChanger, profile) -> {
//            final BaseDirectory META_INF = profile.getStructureProperty().getValue().getDecompressionDir()
//                    .findDirectoriesByNames("META-INF")
//                    .findFirst().orElseThrow(ManifestNotFoundException::new);
//            manifestChanger.apply(META_INF);
//        }, this::onSuccessChangeManifest);
//
//        addOnTransform(SashokTransformator::apply, this::onSuccessTransformation);
//    }
//    public void onBuild() {
//        addOnBuild((builder, profile) -> {
//            builder.setNewJarFile(profile.getStructureProperty().get().getBuildFile());
//            builder.apply(profile.getStructureProperty().getValue().getDecompressionDir().getAllFiles(false));
//        }, this::onSuccessBuild);
//    }
//    public void onRebuild() {
//        addOnRebuild((builder, profile) -> {
//            profile.getStructureProperty().getValue().deleteBuildFile();
//            builder.setNewJarFile(profile.getStructureProperty().getValue().getBuildFile());
//            builder.apply(profile.getStructureProperty().getValue().getDecompressionDir().getAllFiles(false));
//        }, this::onSuccessRebuild);
//    }
//    public void onPrependJFoenix() {
//        addOnAppendJFoenix((appender, profile) -> {
//            appender.setTempFolder(HDirectory.createTempDirectory("jfoenix"));
//            appender.apply(profile.getStructureProperty().getValue().getBuildFile());
//        }, this::onSuccessPrependJFoenixLibrary);
//    }
//    public void onDeleteJFoenix() {
//        addOnDeleteJFoenix((deleter, profile) -> {
//            deleter.apply(profile.getStructureProperty().getValue().getBuildFile());
//        }, this::onSuccessDeleteJFoenixLibrary);
//    }
//    public void onUpdateRuntimeFolder() {
//        addOnUpdateRuntimeFolder((updater, profile) -> {
//            updater.setFilesToBeUpdated("runtime");
//            updater.setFolderWithFiles(profile.getStructureProperty().getValue().getDecompressionDir());
//            updater.apply(profile.getStructureProperty().getValue().getBuildFile());
//        }, this::onSuccessUpdateRuntimeFolder);
//    }
//
//    public abstract <T extends State> void init(@NotNull T state);
//
////    private void debugState(@NotNull Sashok274SettingState currentState) {
////        SessionLogManager.getManager().getLogger().info("Print debug setting state:");
////        SessionLogManager.getManager().getLogger().info("\tDelete build file: " + currentState.shouldDeleteBuildFile());
////        SessionLogManager.getManager().getLogger().info("\tDelete project folder: " + currentState.shouldDeleteProjectFolder());
////        SessionLogManager.getManager().getLogger().info("\tDecompress: " + currentState.shouldUnpack());
////        SessionLogManager.getManager().getLogger().info("\tDelete security: " + currentState.isDeleteSecurity());
////        SessionLogManager.getManager().getLogger().info("\tChange background enabled: " + currentState.isChangeBackgroundEnabled());
////        SessionLogManager.getManager().getLogger().info("\tRebuild: " + currentState.shouldRebuild());
////        SessionLogManager.getManager().getLogger().info("\tBuild: " + currentState.shouldBuild());
////        SessionLogManager.getManager().getLogger().info("\tAdd JFoenix: " + currentState.isAddJFoenix());
////        SessionLogManager.getManager().getLogger().info("\tDelete JFoenix: " + currentState.isDeleteJFoenix());
////        SessionLogManager.getManager().getLogger().info("\tUpdate Runtime folder: " + currentState.shouldUpdateRuntimeFolder());
////    }
//}
