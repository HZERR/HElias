package ru.hzerr.modification.state.impl;

import ru.hzerr.modification.state.strategy.DefaultParameters;
import ru.hzerr.modification.state.strategy.State;
import ru.hzerr.modification.state.strategy.StateBuilder;

public class MythicalWorldStateBuilder extends StateBuilder {

    public MythicalWorldStateBuilder() { super(); }

    public void debugMode(boolean value) { values.put("debugMode", value); }
    public void addJFoenix(boolean value) { values.put("addJFoenix", value); }
    public void removeJFoenix(boolean value) { values.put("removeJFoenix", value); }
    public void replaceRuntimeFolder(boolean value) { values.put("replaceRuntimeFolder", value); }
    public void changeBackground(boolean value) { values.put("changeBackground", value); }
    public void deleteSecurity(boolean value) { values.put("deleteSecurity", value); }
    public void rebuild(boolean value) { values.put("rebuild", value); }
    public void decompress(boolean value) { values.put("decompress", value); }
    public void deleteBuildFile(boolean value) { values.put("deleteBuildFile", value); }
    public void deleteProjectFolder(boolean value) { values.put("deleteProjectFolder", value); }
    public void construct(boolean value) { values.put("construct", value); }

    @Override
    public State build() {
        final State mythicalWorld = new MythicalWorldState(values);
        mythicalWorld.setName("MythicalWorld");
        return mythicalWorld;
    }

    @Override
    public State buildDefaultState() {
        debugMode(DefaultParameters.DEBUG_MODE.isEnabled());
        addJFoenix(DefaultParameters.PREPEND_JFOENIX.isEnabled());
        removeJFoenix(DefaultParameters.REMOVE_JFOENIX.isEnabled());
        replaceRuntimeFolder(DefaultParameters.UPDATE_RUNTIME_FOLDER.isEnabled());
        changeBackground(DefaultParameters.CHANGE_BACKGROUND.isEnabled());
        deleteSecurity(DefaultParameters.DELETE_SECURITY.isEnabled());
        rebuild(DefaultParameters.REBUILD.isEnabled());
        decompress(DefaultParameters.DECOMPRESS.isEnabled());
        deleteBuildFile(DefaultParameters.DELETE_BUILD_FILE.isEnabled());
        deleteProjectFolder(DefaultParameters.DELETE_PROJECT_FOLDER.isEnabled());
        construct(DefaultParameters.CONSTRUCT.isEnabled());
        return build();
    }

    @SuppressWarnings("DuplicatedCode")
    public static MythicalWorldStateBuilder defaultBuilder() {
        MythicalWorldStateBuilder instance =  new MythicalWorldStateBuilder();
        instance.debugMode(DefaultParameters.DEBUG_MODE.isEnabled());
        instance.addJFoenix(DefaultParameters.PREPEND_JFOENIX.isEnabled());
        instance.removeJFoenix(DefaultParameters.REMOVE_JFOENIX.isEnabled());
        instance.replaceRuntimeFolder(DefaultParameters.UPDATE_RUNTIME_FOLDER.isEnabled());
        instance.changeBackground(DefaultParameters.CHANGE_BACKGROUND.isEnabled());
        instance.deleteSecurity(DefaultParameters.DELETE_SECURITY.isEnabled());
        instance.rebuild(DefaultParameters.REBUILD.isEnabled());
        instance.decompress(DefaultParameters.DECOMPRESS.isEnabled());
        instance.deleteBuildFile(DefaultParameters.DELETE_BUILD_FILE.isEnabled());
        instance.deleteProjectFolder(DefaultParameters.DELETE_PROJECT_FOLDER.isEnabled());
        instance.construct(DefaultParameters.CONSTRUCT.isEnabled());
        return instance;
    }
}
