//package ru.hzerr.modification.chain.builder.impl;
//
//import javafx.scene.control.Label;
//import ru.hzerr.HElias;
//import ru.hzerr.config.listener.content.Content;
//import ru.hzerr.modification.Project;
//
//import java.text.NumberFormat;
//
//@Deprecated
//public class MythicalWorldChainBuilderImpl extends MythicalWorldChainBuilder {
//
//    private Label state;
//    private int countEnabled;
//    private double progress = 0D;
//    private final NumberFormat nf = NumberFormat.getPercentInstance(HElias.getProperties().getLanguage().getBundle().getLocale());
//
//    public MythicalWorldChainBuilderImpl(Project project) {
//        super(project);
//    }
//
//    @Override
//    public MythicalWorldChainBuilder installContent(Content userData) {
//        checkArguments(userData, "state", "countEnabled");
//        this.state = userData.get("state", Label.class);
//        this.countEnabled = userData.get("countEnabled", Integer.class);
//        return this;
//    }
//
//    @Override
//    public void onSuccessBuildFileDeletion() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessProjectFolderDeletion() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessDecompression() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessChangeManifest() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessTransformation() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessChangeBackground() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessBuild() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessRebuild() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessPrependJFoenixLibrary() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessDeleteJFoenixLibrary() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//
//    @Override
//    public void onSuccessUpdateRuntimeFolder() {
//        progress += 1D / countEnabled;
//        state.setText(nf.format(progress));
//    }
//}
