package ru.hzerr.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import ru.hzerr.annotation.Preinstall;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.config.listener.BaseEventListener;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.loaders.Language;
import ru.hzerr.loaders.LanguageLoader;
import ru.hzerr.loaders.theme.ThemeLoader;
import ru.hzerr.log.SessionLogManager;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

public class Properties {

    private final BaseDirectory ROOT_DIR = new HDirectory(System.getProperty("user.home").concat(File.separator).concat("HElias"));
    private final BaseDirectory MODIFICATION_DIR = ROOT_DIR.getSubDirectory("modification");
    private final BaseDirectory ASSETS_DIR = MODIFICATION_DIR.getSubDirectory("assets");
    /**
     * backgrounds -> mcskill.png
     *             -> mythical-world.svg
     */
    private final BaseDirectory BACKGROUNDS_DIR = ASSETS_DIR.getSubDirectory("backgrounds");
    private final BaseDirectory CONFIG_DIR = ROOT_DIR.getSubDirectory("config");
    private final BaseFile CONFIG_FILE = CONFIG_DIR.getSubFile("config.properties");
    private final BaseDirectory PROJECTS_DIR = MODIFICATION_DIR.getSubDirectory("projects");
    private final BaseDirectory LOG_DIR = ROOT_DIR.getSubDirectory("log");

    private HList<Profile> cacheProfiles;

    @Preinstall
    private static PropertiesConfiguration CONFIG;
    private static final Properties INSTANCE = new Properties();
    private final ObjectProperties objectProperties = new ObjectProperties(CONFIG_DIR);

    protected Properties() {
    }

    public <T extends BaseEventListener<?>> void addListener(T eventListener) {
        CONFIG.addEventListener(ConfigurationEvent.SET_PROPERTY, eventListener);
    }

    public <T extends BaseEventListener<?>> void removeListener(T eventListener) {
        CONFIG.removeEventListener(ConfigurationEvent.SET_PROPERTY, eventListener);
    }

    public BaseDirectory getLogDir() { return LOG_DIR; }
    public BaseDirectory getBackgroundDir() { return BACKGROUNDS_DIR; }
    public BaseDirectory getRootDir() { return ROOT_DIR; }
    public BaseDirectory getModificationDir() { return MODIFICATION_DIR; }
    public BaseDirectory getConfigDir() { return CONFIG_DIR; }
    public BaseFile getConfigFile() { return CONFIG_FILE; }
    public BaseDirectory getProjectsDir() { return PROJECTS_DIR; }

    public void addProfile(Profile profile) {
        try {
            if (objectProperties.checkKeyNotFoundInGroup("profiles", profile.getProfileNameProperty().getValue())) {
                objectProperties.saveToGroup("profiles", profile.getProfileNameProperty().getValue(), profile);
            }
        } catch (IOException io) { throw new UncheckedIOException(io); }
    }
    public void removeProfile(Profile profile) {
        try {
            if (objectProperties.checkKeyExistsInGroup("profiles", profile.getProfileNameProperty().getValue())) {
                objectProperties.removeFromGroup("profiles", profile.getProfileNameProperty().getValue());
                profile.deleteProfile();
            }
        } catch (IOException io) { throw new UncheckedIOException(io); }
    }
    public Profile getProfileByName(String profileName) {
        try {
            if (objectProperties.checkExistsGroup("profiles")) {
                return objectProperties.loadFromGroup("profiles", profileName, (Profile) null);
            }
        } catch (IOException io) { throw new UncheckedIOException(io); }

        return null;
    }
    public HList<Profile> getProfiles() {
        try {
            if (objectProperties.checkExistsGroup("profiles")) {
                return objectProperties.loadAllFromGroup("profiles");
            }
        } catch (IOException io) { throw new UncheckedIOException(io); }

        return new ArrayHList<>();
    }

    public boolean isEmptyProfiles() { return objectProperties.isEmptyGroup("profiles"); }
    public boolean checkExistsProfileWithName(String profileName) {
        return objectProperties.checkKeyExistsInGroup("profiles", profileName);
    }
    public String getDefaultProfileName() { return CONFIG.getString("profile.by.default", ""); }

    /**
     * Gets the default profile
     * @return default profile or null
     * @throws UncheckedIOException if I/O error
     */
    public Optional<Profile> getDefaultProfile() {
        String defaultProfileName = CONFIG.getString("profile.by.default", "");
        if (StringUtils.isNotEmpty(defaultProfileName)) {
            try {
                if (objectProperties.checkExistsGroup("profiles")) {
                    if (objectProperties.checkKeyExistsInGroup("profiles", defaultProfileName)) {
                        return Optional.ofNullable(objectProperties.loadFromGroup("profiles", defaultProfileName, (Profile) null));
                    }
                }
            } catch (IOException io) {
                throw new UncheckedIOException(io);
            }
        }

        return Optional.empty();
    }
    public void setDefaultProfile(Profile profile) { CONFIG.setProperty("profile.by.default", profile.getProfileNameProperty().getValue()); }
    public boolean isDefaultProfile(Profile profile) { return this.getDefaultProfileName().equals(profile.getProfileNameProperty().getValue()); }
    public void clearDefaultProfile() { CONFIG.setProperty("profile.by.default", ""); }

    public ThemeLoader.ThemeType getTheme() { return CONFIG.get(ThemeLoader.ThemeType.class, "theme"); }
    public void setTheme(ThemeLoader.ThemeType theme) { CONFIG.setProperty("theme", theme); }
    public BaseFile getMcSkillBackground() { return BACKGROUNDS_DIR.getSubFile("mcskill"); }
    public BaseFile getMythicalWorldBackground() { return BACKGROUNDS_DIR.getSubFile("mythical-world"); }
    public void setMcSkillBackground(BaseFile background) throws IOException {
        BaseFile oldMcSkillBackground = getMcSkillBackground();
        if (oldMcSkillBackground.exists()) oldMcSkillBackground.delete();
        background.copyToFile(BACKGROUNDS_DIR.createSubFile("mcskill." + background.getExtension()));
    }

    public void setMythicalWorldBackground(BaseFile background) throws IOException {
        BaseFile oldMythicalWorldBackground = getMythicalWorldBackground();
        if (oldMythicalWorldBackground.exists()) oldMythicalWorldBackground.delete();
        background.copyToFile(BACKGROUNDS_DIR.createSubFile("mythical-world." + background.getExtension()));
    }

    public boolean shouldUseClassesFromBuildFile() { return CONFIG.getBoolean("use.classes.from.build.file", false); }
    public void setUseClassesFromBuildFile(boolean shouldUseClassesFromBuildFile) { CONFIG.setProperty("use.classes.from.build.file", shouldUseClassesFromBuildFile); }
    public boolean isExpertMode() { return CONFIG.getBoolean("expert.mode", false); }
    public void setExpertMode(boolean isExpertMode) { CONFIG.setProperty("expert.mode", isExpertMode);}
    public String getPathToInstalledProject() { return CONFIG.getString("path.to.installed.project", ""); }
    public void setPathToInstalledProject(String pathToInstalledProject) { CONFIG.setProperty("path.to.installed.project", pathToInstalledProject); }
    public Language getLanguage() { return LanguageLoader.getLanguage(LanguageLoader.LanguageType.byType(CONFIG.getString("language", "en"))); }
    public void setLanguage(Language language) { CONFIG.setProperty("language", language.getName()); }
    public String getProjectFullName() { return CONFIG.getString("project.full.name", ""); }
    public void setProjectFullName(String projectFullName) { CONFIG.setProperty("project.full.name", projectFullName); }
    public String getProjectTestName() { return CONFIG.getString("project.build.name", ""); }
    public void setProjectTestName(String projectTestName) { CONFIG.setProperty("project.build.name", projectTestName); }
    public boolean isDebugForceEnabled() { return CONFIG.getBoolean("debug.force.enabled", false); }
    public void setForceDebug(boolean isDebugForceEnabled) { CONFIG.setProperty("debug.force.enabled", isDebugForceEnabled); }
    public String getLauncherName() { return CONFIG.getString("launcher.name", ""); }
    public void setLauncherName(String launcherName) { CONFIG.setProperty("launcher.name", launcherName); }
    public String getLauncherVersion() { return CONFIG.getString("launcher.version", ""); }
    public void setLauncherVersion(String launcherVersion) { CONFIG.setProperty("launcher.version", launcherVersion); }
    public String getLauncherBuild() { return CONFIG.getString("launcher.build", ""); }
    public void setLauncherBuild(String launcherBuild) { CONFIG.setProperty("launcher.build", launcherBuild); }
    public String getSecurityHelperClassName() { return CONFIG.getString("security.helper.class.name", ""); }
    public void setSecurityHelperClassName(String securityHelperClassName) { CONFIG.setProperty("security.helper.class.name", securityHelperClassName); }
    public String getClientLauncherClassName() { return CONFIG.getString("client.launcher.class.name", ""); }
    public void setClientLauncherClassName(String clientLauncherClassName) { CONFIG.setProperty("client.launcher.class.name", clientLauncherClassName); }
    public String getIOHelperClassName() { return CONFIG.getString("io.helper.class.name", ""); }
    public void setIOHelperClassName(String helperClassName) { CONFIG.setProperty("io.helper.class.name", helperClassName); }
    public String getLauncherClassName() { return CONFIG.getString("launcher.class.name", ""); }
    public void setLauncherClassName(String launcherClassName) { CONFIG.setProperty("launcher.class.name", launcherClassName); }
    public String getVerifyHelperClassName() { return CONFIG.getString("verify.helper.class.name", ""); }
    public void setVerifyHelperClassName(String verifyHelperClassName) { CONFIG.setProperty("verify.helper.class.name", verifyHelperClassName); }
    public String getLogHelperClassName() { return CONFIG.getString("log.helper.class.name", ""); }
    public void setLogHelperClassName(String logHelperClassName) { CONFIG.setProperty("log.helper.class.name", logHelperClassName); }
    public String getUpdateRequestClassName() { return CONFIG.getString("update.request.class.name", ""); }
    public void setUpdateRequestClassName(String updateRequestClassName) { CONFIG.setProperty("update.request.class.name", updateRequestClassName); }
    public void installMcSkillClassNames() {
        this.setSecurityHelperClassName(getMcSkillSecurityHelperClassName());
        this.setClientLauncherClassName(getMcSkillClientLauncherClassName());
        this.setIOHelperClassName(getMcSkillIOHelperClassName());
        this.setLauncherClassName(getMcSkillLauncherClassName());
        this.setVerifyHelperClassName(getMcSkillVerifyHelperClassName());
        this.setLogHelperClassName(getMcSkillLogHelperClassName());
        this.setUpdateRequestClassName(getMcSkillUpdateRequestClassName());
    }

    public void installMythicalWorldClassNames() {
        this.setSecurityHelperClassName(getMythicalWorldSecurityHelperClassName());
        this.setClientLauncherClassName(getMythicalWorldClientLauncherClassName());
        this.setIOHelperClassName(getMythicalWorldIOHelperClassName());
        this.setLauncherClassName(getMythicalWorldLauncherClassName());
        this.setVerifyHelperClassName(getMythicalWorldVerifyHelperClassName());
        this.setLogHelperClassName(getMythicalWorldLogHelperClassName());
        this.setUpdateRequestClassName(getMythicalWorldUpdateRequestClassName());
    }

    public String getMcSkillSecurityHelperClassName() { return CONFIG.getString("mc.skill.security.helper.class.name", ""); }
    public String getMcSkillClientLauncherClassName() { return CONFIG.getString("mc.skill.client.launcher.class.name", ""); }
    public String getMcSkillIOHelperClassName() { return CONFIG.getString("mc.skill.io.helper.class.name", ""); }
    public String getMcSkillLauncherClassName() { return CONFIG.getString("mc.skill.launcher.class.name", ""); }
    public String getMcSkillVerifyHelperClassName() { return CONFIG.getString("mc.skill.verify.helper.class.name", ""); }
    public String getMcSkillLogHelperClassName() { return CONFIG.getString("mc.skill.log.helper.class.name", ""); }
    public String getMcSkillUpdateRequestClassName() { return CONFIG.getString("mc.skill.update.request.class.name", ""); }

    public String getMythicalWorldSecurityHelperClassName() { return CONFIG.getString("mythical.world.security.helper.class.name", ""); }
    public String getMythicalWorldClientLauncherClassName() { return CONFIG.getString("mythical.world.client.launcher.class.name", ""); }
    public String getMythicalWorldIOHelperClassName() { return CONFIG.getString("mythical.world.io.helper.class.name", ""); }
    public String getMythicalWorldLauncherClassName() { return CONFIG.getString("mythical.world.launcher.class.name", ""); }
    public String getMythicalWorldVerifyHelperClassName() { return CONFIG.getString("mythical.world.verify.helper.class.name", ""); }
    public String getMythicalWorldLogHelperClassName() { return CONFIG.getString("mythical.world.log.helper.class.name", ""); }
    public String getMythicalWorldUpdateRequestClassName() { return CONFIG.getString("mythical.world.update.request.class.name", ""); }

    public void init() throws ConfigurationException, IOException {
        // create if needed
        ROOT_DIR.create();
        MODIFICATION_DIR.create();
        CONFIG_DIR.create();
        CONFIG_FILE.create();
        ASSETS_DIR.create();
        BACKGROUNDS_DIR.create();
        PROJECTS_DIR.create();
        LOG_DIR.create();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setFile(CONFIG_FILE.asIOFile())
                                .setThrowExceptionOnMissing(true)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                                .setIncludesAllowed(true));
        builder.setAutoSave(true);
        CONFIG = builder.getConfiguration();
        if (CONFIG.isEmpty()) {
            CONFIG.addProperty("language", "en");
            CONFIG.addProperty("theme", ThemeLoader.ThemeType.DARK);
            CONFIG.addProperty("expert.mode", false);
            CONFIG.addProperty("use.classes.from.build.file", false);
            CONFIG.addProperty("profile.by.default", "");
            // Modification Properties
            CONFIG.addProperty("project.full.name", "");
            CONFIG.addProperty("project.build.name", "HLauncher.jar");
            CONFIG.addProperty("launcher.name", "HZERR");
            CONFIG.addProperty("launcher.version", "1.0");
            CONFIG.addProperty("launcher.build", "1, 20.08.2021");
            CONFIG.addProperty("path.to.installed.project", "");
            CONFIG.addProperty("debug.force.enabled", false);
            // Default class names
            CONFIG.addProperty("security.helper.class.name", "");
            CONFIG.addProperty("client.launcher.class.name", "");
            CONFIG.addProperty("io.helper.class.name", "");
            CONFIG.addProperty("launcher.class.name", "");
            CONFIG.addProperty("verify.helper.class.name", "");
            CONFIG.addProperty("log.helper.class.name", "");
            CONFIG.addProperty("update.request.class.name", "");
            // MythicalWorld class names
            CONFIG.addProperty("mythical.world.security.helper.class.name", "launcher.KeepErJeRrY4b2");
            CONFIG.addProperty("mythical.world.client.launcher.class.name", "launcher.keEpErjeRRYsAK");
            CONFIG.addProperty("mythical.world.io.helper.class.name", "launcher.KeepErjERryKEt");
            CONFIG.addProperty("mythical.world.launcher.class.name", "launcher.keepErJErRy6HE");
            CONFIG.addProperty("mythical.world.verify.helper.class.name", "launcher.keePerJerryeTT");
            CONFIG.addProperty("mythical.world.log.helper.class.name", "launcher.KEePErJeRRygYU");
            CONFIG.addProperty("mythical.world.update.request.class.name", "deprecated");
            // McSkill class names
            CONFIG.addProperty("mc.skill.security.helper.class.name", "launcher.COM1");
            CONFIG.addProperty("mc.skill.client.launcher.class.name", "launcher.AUx");
            CONFIG.addProperty("mc.skill.verify.helper.class.name", "launcher.Com2");
            CONFIG.addProperty("mc.skill.io.helper.class.name", "launcher.PRn");
            CONFIG.addProperty("mc.skill.launcher.class.name", "launcher.aux");
            CONFIG.addProperty("mc.skill.log.helper.class.name", "launcher.Com1");
            CONFIG.addProperty("mc.skill.update.request.class.name", "launcher.Com4");
        }
        // Initialization of the log manager, creation of the current session
        SessionLogManager.getManager().createSession();
        // Initializing the object properties
        objectProperties.init();
        // Initializing the profile cache
        if (objectProperties.checkExistsGroup("profiles")) {
            cacheProfiles = objectProperties.loadAllFromGroup("profiles");
        } else cacheProfiles = HList.newList();
    }

    public static Properties getInstance() { return INSTANCE; }
}
