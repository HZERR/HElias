package ru.hzerr.config.profile.settings;

import ru.hzerr.file.BaseDirectory;

import java.io.Serializable;

public class ServerData implements Serializable {

    private static final long serialVersionUID = 1L;

    // watch directory
    private BaseDirectory installedProjectDirectory;
    private BaseDirectory selectedServerDirectory;
    private String serverName;

    public ServerData() {
    }

    public void setInstalledProjectDirectory(BaseDirectory installedProjectDirectory) {
        this.installedProjectDirectory = installedProjectDirectory;
    }

    public BaseDirectory getInstalledProjectDirectory() {
        return installedProjectDirectory;
    }

    public void setSelectedServerDirectory(BaseDirectory selectedServerDirectory) {
        this.selectedServerDirectory = selectedServerDirectory;
    }

    public BaseDirectory getSelectedServerDirectory() {
        return selectedServerDirectory;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public boolean isCreatedInFuture() {
        return serverName == null && selectedServerDirectory == null && installedProjectDirectory != null;
    }
}
