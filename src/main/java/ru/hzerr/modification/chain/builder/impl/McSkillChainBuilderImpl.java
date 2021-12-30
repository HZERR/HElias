package ru.hzerr.modification.chain.builder.impl;

import javafx.scene.control.Label;
import org.apache.commons.lang3.StringUtils;
import ru.hzerr.config.listener.content.Content;
import ru.hzerr.modification.Project;

@Deprecated
public class McSkillChainBuilderImpl extends McSkillChainBuilder {

    private Label state;
    private int countEnabled;

    public McSkillChainBuilderImpl(Project project) {
        super(project);
    }

    @Override
    public McSkillChainBuilder installContent(Content userData) {
        checkArguments(userData, "state", "countEnabled");
        this.state = userData.get("state", Label.class);
        this.countEnabled = userData.get("countEnabled", Integer.class);
        return this;
    }

    @Override
    public void onSuccessBuildFileDeletion() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessProjectFolderDeletion() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessDecompression() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessChangeManifest() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessTransformation() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessChangeBackground() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessBuild() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessRebuild() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessPrependJFoenixLibrary() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessDeleteJFoenixLibrary() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }

    @Override
    public void onSuccessUpdateRuntimeFolder() {
        double newProgress = Double.parseDouble(StringUtils.chop(state.getText())) + 1D / countEnabled;
        state.setText(newProgress + "%");
    }
}
