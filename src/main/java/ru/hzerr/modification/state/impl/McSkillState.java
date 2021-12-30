package ru.hzerr.modification.state.impl;

import ru.hzerr.collections.map.HMap;
import ru.hzerr.modification.state.strategy.State;

public class McSkillState extends State {

    public McSkillState() { super(); }
    McSkillState(HMap<String, Object> values) { super(values); }

    public void setDebugMode(boolean debugMode) { super.values.put("debugMode", debugMode); }
    public boolean isDebugModeEnabled() {
        Boolean debugMode = super.getBoolean("debugMode");
        return resultOrDefault(debugMode);
    }

    public void setPrependJFoenix(boolean addJFoenix) { super.values.put("addJFoenix", addJFoenix); }
    public boolean isPrependJFoenix() {
        Boolean addJFoenix = super.getBoolean("addJFoenix");
        return resultOrDefault(addJFoenix);
    }

    public void setRemoveJFoenix(boolean removeJFoenix) { super.values.put("removeJFoenix", removeJFoenix); }
    public boolean isRemoveJFoenix() {
        Boolean removeJFoenix = super.getBoolean("removeJFoenix");
        return resultOrDefault(removeJFoenix);
    }

    public void setReplaceRuntimeFolder(boolean replaceRuntimeFolder) { super.values.put("replaceRuntimeFolder", replaceRuntimeFolder); }
    public boolean isReplaceRuntimeFolder() {
        Boolean replaceRuntimeFolder = super.getBoolean("replaceRuntimeFolder");
        return resultOrDefault(replaceRuntimeFolder);
    }

    public void setChangeBackground(boolean changeBackground) { super.values.put("changeBackground", changeBackground); }
    public boolean isChangeBackground() {
        Boolean changeBackground = super.getBoolean("changeBackground");
        return resultOrDefault(changeBackground);
    }

    public void setDeleteSecurity(boolean deleteSecurity) { super.values.put("deleteSecurity", deleteSecurity); }
    public boolean isDeleteSecurity() {
        Boolean deleteSecurity = super.getBoolean("deleteSecurity");
        return resultOrDefault(deleteSecurity);
    }

    public void setRebuild(boolean rebuild) { super.values.put("rebuild", rebuild); }
    public boolean isRebuild() {
        Boolean rebuild = super.getBoolean("rebuild");
        return resultOrDefault(rebuild);
    }

    public void setDecompress(boolean decompress) { super.values.put("decompress", decompress); }
    public boolean isDecompress() {
        Boolean decompress = super.getBoolean("decompress");
        return resultOrDefault(decompress);
    }

    public void setDeleteBuildFile(boolean deleteBuildFile) { super.values.put("deleteBuildFile", deleteBuildFile); }
    public boolean isDeleteBuildFile() {
        Boolean deleteBuildFile = super.getBoolean("deleteBuildFile");
        return resultOrDefault(deleteBuildFile);
    }

    public void setCleanupProjectFolder(boolean cleanupProjectFolder) { super.values.put("cleanupProjectFolder", cleanupProjectFolder); }
    public boolean isCleanupProjectFolder() {
        Boolean cleanupProjectFolder = super.getBoolean("cleanupProjectFolder");
        return resultOrDefault(cleanupProjectFolder);
    }

    public void setConstruct(boolean construct) { super.values.put("construct", construct); }
    public boolean isConstruct() {
        Boolean construct = super.getBoolean("construct");
        return resultOrDefault(construct);
    }

    @Override
    public int count(State state) {
        int count = 0;
        switch (state) {
            case DISABLED:
                for (Object value: values.values()) {
                    if (value instanceof Boolean) {
                        if (!((Boolean) value)) count++;
                    }
                }
                break;
            case ENABLED:
                for (Object value: values.values()) {
                    if (value instanceof Boolean) {
                        if (((Boolean) value)) count++;
                    }
                }
                break;
            case ALL:
                count = values.values().size();
                break;
        }

        return count;
    }

    private boolean resultOrDefault(Boolean parameter) {
        return parameter != null ? parameter : false;
    }
}
