package ru.hzerr.config.profile.settings;

import ru.hzerr.HElias;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.file.BaseFile;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GlobalSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String buildFileName;
    private String launcherName;
    private String launcherVersion;
    private String launcherBuild;
    private ServerData selectedServer;
    private BaseFile java;

    public GlobalSettings() {
    }

    public void setBuildFileName(String buildFileName) {
        this.buildFileName = buildFileName;
    }

    public String getBuildFileName() {
        return buildFileName;
    }

    public void setLauncherName(String launcherName) {
        this.launcherName = launcherName;
    }

    public String getLauncherName() {
        return launcherName;
    }

    public void setLauncherVersion(String launcherVersion) {
        this.launcherVersion = launcherVersion;
    }

    public String getLauncherVersion() {
        return launcherVersion;
    }

    public void setLauncherBuild(String launcherBuild) {
        this.launcherBuild = launcherBuild;
    }

    public String getLauncherBuild() {
        return launcherBuild;
    }

    public void setSelectedServer(ServerData selectedServer) {
        this.selectedServer = selectedServer;
    }

    public ServerData getSelectedServer() {
        return selectedServer;
    }

    public void setJava(BaseFile java) {
        this.java = java;
    }

    public BaseFile getJava() {
        return java;
    }

    static GlobalSettings makeCopy() {
        GlobalSettings settings = new GlobalSettings();
        if (HElias.getProperties().isEmptyProfiles()) {
            settings.setBuildFileName("HLauncher.jar");
            settings.setLauncherName("HZERR");
            settings.setLauncherVersion("1.0");
            settings.setLauncherBuild("1, " + new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
        } else {
            Profile profile = HElias.getProperties().getProfiles().lastElement();
            settings.setBuildFileName(profile.getSettingsProperty().getValue().getGlobalSettings().getBuildFileName());
            settings.setLauncherName(profile.getSettingsProperty().getValue().getGlobalSettings().getLauncherName());
            settings.setLauncherVersion(profile.getSettingsProperty().getValue().getGlobalSettings().getLauncherVersion());
            settings.setLauncherBuild(profile.getSettingsProperty().getValue().getGlobalSettings().getLauncherBuild());
        }

        return settings;
    }
}
