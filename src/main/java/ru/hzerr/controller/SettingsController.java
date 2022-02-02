package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.hzerr.HElias;
import ru.hzerr.config.PropertyNames;
import ru.hzerr.config.listener.BooleanEventListener;
import ru.hzerr.config.listener.WrapperChangeListener;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.config.profile.Structure;
import ru.hzerr.controller.popup.ChoiceInstalledProjectDirectoryController;
import ru.hzerr.controller.popup.ChoiceTextController;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.loaders.LanguageLoader;
import ru.hzerr.loaders.theme.ThemeLoader;
import ru.hzerr.log.LogManager;
import ru.hzerr.util.Fx;
import ru.hzerr.util.Instruments;
import ru.hzerr.util.LoggableThread;
import ru.hzerr.util.SystemInfo;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SettingsController {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private JFXButton changeNameProjectButton;
    @FXML private JFXButton changeNameLauncherButton;
    @FXML private JFXButton changeVersionLauncherButton;
    @FXML private JFXButton changeBuildLauncherButton;
    @FXML private JFXButton openFolderButton;
    @FXML private HBox languages;
    @FXML private JFXRadioButton russian;
    @FXML private JFXRadioButton english;
    @FXML private JFXButton changePathToInstalledProject;
    @FXML private VBox themes;
    @FXML private JFXRadioButton enableDragonTheme;
    @FXML private JFXButton changePathToJava;
    @FXML private JFXToggleButton enableExpertMode;
    @FXML private Label stateBuildName;
    @FXML private Label stateLauncherName;
    @FXML private Label stateLauncherVersion;
    @FXML private Label stateLauncherBuild;
    @FXML private Label statePathToInstalledProject;
    @FXML private Label statePathToJava;

    @FXML
    void onChangeBuildName(ActionEvent event) {
        Optional<Profile> profile = HElias.getProperties().getDefaultProfile();
        if (profile.isPresent()) {
            ChoiceTextController choiceTextController = new ChoiceTextController();
            choiceTextController.setOldValue(profile.get().getSettingsProperty().getValue().getGlobalSettings().getBuildFileName());
            choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.name.project.build"));
            choiceTextController.setError(resources.getString("popup.choice.text.error.change.name.project.build"));
            choiceTextController.setPattern(Pattern.compile("^[\\w-.]+\\.jar$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS));
            choiceTextController.setOnFinished(selectedValue -> {
                profile.get().getSettingsProperty().getValue().getGlobalSettings().setBuildFileName(selectedValue);
                stateBuildName.getStyleClass().remove("install-no");
                stateBuildName.getStyleClass().add("install-yes");
                stateBuildName.setText(resources.getString("tab.settings.state.install.yes"));
            });
            FXMLLoader.showSafePopup("choice-text", resources, choiceTextController);
        } else ErrorSupport.showWarningPopup(resources.getString("popup.warning.no.such.default.profile.title"), resources.getString("popup.warning.no.such.default.profile.message"));
    }

    @FXML
    void onChangeLauncherBuild(ActionEvent event) {
        Optional<Profile> profile = HElias.getProperties().getDefaultProfile();
        if (profile.isPresent()) {
            ChoiceTextController choiceTextController = new ChoiceTextController();
            choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.build.launcher"));
            choiceTextController.setError(resources.getString("popup.choice.text.error.change.build.launcher"));
            choiceTextController.setOldValue(profile.get().getSettingsProperty().getValue().getGlobalSettings().getLauncherBuild());
            choiceTextController.setOnFinished(selectedValue -> {
                profile.get().getSettingsProperty().getValue().getGlobalSettings().setLauncherBuild(selectedValue);
                stateLauncherBuild.getStyleClass().remove("install-no");
                stateLauncherBuild.getStyleClass().add("install-yes");
                stateLauncherBuild.setText(resources.getString("tab.settings.state.install.yes"));
            });
            FXMLLoader.showSafePopup("choice-text", resources, choiceTextController);
        } else ErrorSupport.showWarningPopup(resources.getString("popup.warning.no.such.default.profile.title"), resources.getString("popup.warning.no.such.default.profile.message"));
    }

    @FXML
    void onChangeLauncherName(ActionEvent event) {
        Optional<Profile> profile = HElias.getProperties().getDefaultProfile();
        if (profile.isPresent()) {
            ChoiceTextController choiceTextController = new ChoiceTextController();
            choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.name.launcher"));
            choiceTextController.setError(resources.getString("popup.choice.text.error.change.name.launcher"));
            choiceTextController.setOldValue(profile.get().getSettingsProperty().getValue().getGlobalSettings().getLauncherName());
            choiceTextController.setOnFinished(selectedValue -> {
                profile.get().getSettingsProperty().getValue().getGlobalSettings().setLauncherName(selectedValue);
                stateLauncherName.getStyleClass().remove("install-no");
                stateLauncherName.getStyleClass().add("install-yes");
                stateLauncherName.setText(resources.getString("tab.settings.state.install.yes"));
            });
            FXMLLoader.showSafePopup("choice-text", resources, choiceTextController);
        } else ErrorSupport.showWarningPopup(resources.getString("popup.warning.no.such.default.profile.title"), resources.getString("popup.warning.no.such.default.profile.message"));
    }

    @FXML
    void onChangeLauncherVersion(ActionEvent event) {
        Optional<Profile> profile = HElias.getProperties().getDefaultProfile();
        if (profile.isPresent()) {
            ChoiceTextController choiceTextController = new ChoiceTextController();
            choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.version.launcher"));
            choiceTextController.setError(resources.getString("popup.choice.text.error.change.version.launcher"));
            choiceTextController.setOldValue(profile.get().getSettingsProperty().getValue().getGlobalSettings().getLauncherVersion());
            choiceTextController.setOnFinished(selectedValue -> {
                profile.get().getSettingsProperty().getValue().getGlobalSettings().setLauncherVersion(selectedValue);
                stateLauncherVersion.getStyleClass().remove("install-no");
                stateLauncherVersion.getStyleClass().add("install-yes");
                stateLauncherVersion.setText(resources.getString("tab.settings.state.install.yes"));
            });
            FXMLLoader.showSafePopup("choice-text", resources, choiceTextController);
        } else ErrorSupport.showWarningPopup(resources.getString("popup.warning.no.such.default.profile.title"), resources.getString("popup.warning.no.such.default.profile.message"));
    }

    @FXML
    void onChangePathToInstalledProject(ActionEvent event) {
        try {
            ChoiceInstalledProjectDirectoryController controller = new ChoiceInstalledProjectDirectoryController();
            FXMLLoader.showPopup("choice-project-folder", resources, controller);
//            DirectoryChooser projectChooser = new DirectoryChooser();
//            projectChooser.setTitle(resources.getString("file.chooser.title.path.to.installed.project"));
//            projectChooser.setInitialDirectory(new File(SystemInfo.getUserHome()));
//            ChoiceDirectoryController choiceDirectoryController = new ChoiceDirectoryController();
//            choiceDirectoryController.setExplorer(projectChooser);
//            choiceDirectoryController.setValue(HElias.getProperties().getPathToInstalledProject());
//            choiceDirectoryController.setOnFinished(selectedValue -> {
//                HElias.getProperties().setPathToInstalledProject(selectedValue);
//                pathToInstalledProjectLabel.setText(selectedValue);
//            });
//            FXMLLoader.showPopup("choice-file", resources, choiceDirectoryController);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
    }

    @FXML
    void onChangePathToJava(ActionEvent event) {

    }

    @FXML
    void onEnableDragonTheme(ActionEvent event) {

    }

    @FXML
    void onEnableExpertMode(ActionEvent event) {

    }

    @FXML
    void onOpenProfileFolder(ActionEvent event) {
        new LoggableThread(() -> {
            Optional<Profile> defaultProfile = HElias.getProperties().getDefaultProfile();
            if (defaultProfile.isPresent()) {
                Structure structure = defaultProfile.get().getStructureProperty().get();
                BaseDirectory profileRootDir = structure.getRootDir();
                if (profileRootDir.exists()) {
                    if (SystemInfo.isLinux()) {
                        Instruments.run("xdg-open", profileRootDir.getLocation());
//                                new ProcessBuilder("xdg-open", profileRootDir.getLocation()).inheritIO().start();
                    } else Desktop.getDesktop().open(profileRootDir.asIOFile());
                } else ErrorSupport.showWarningPopup(resources.getString("tab.settings.warning.popup.no.such.project.folder.title"), resources.getString("tab.settings.warning.popup.no.such.project.folder.message"));
            } else ErrorSupport.showWarningPopup(resources.getString("tab.settings.warning.popup.no.define.default.profile.title"), resources.getString("tab.settings.warning.popup.no.define.default.profile.message"));
        }).start();
    }

    @FXML
    void onSwitchEnglishLanguage(ActionEvent event) {

    }

    @FXML
    void onSwitchRussianLanguage(ActionEvent event) {

    }

    // ПЕРЕПИСАТЬ ЛОГИКУ SELECT-А ЭКСПЕРТ МОДА
    public void initialize() {
        enableExpertMode.setSelected(HElias.getProperties().isExpertMode()); // set expert mode
        enableExpertMode.selectedProperty().addListener((observable, o, n) -> HElias.getProperties().setExpertMode(n));
        LogManager.getLogger().debug("Expert mode enabled: " + HElias.getProperties().isExpertMode());
        HElias.getProperties().addListener(new BooleanEventListener(PropertyNames.EXPERT_MODE) {

            @Override
            public void onRun(Boolean newValue) {
                LogManager.getLogger().debug("Expert mode was changed: " + newValue);
                enableExpertMode.setSelected(newValue);
            }
        });
        if (HElias.getProperties().getLanguage().equals(LanguageLoader.getRussianLanguage())) russian.setSelected(true);
        else english.setSelected(true);

        /**
         * There is a bug with displaying when loading a theme
         * Everything works perfectly with JDK Amazon Corretto
         * However, not all launchers are launched with JDK Amazon Corretto
         * because of the bug in {@link com.sun.webkit.WebPage#twkCreatePage}
         * JDK Amazon Corretto -> Bug with {@link com.sun.webkit.WebPage#twkCreatePage}
         * Other JDK -> Bug with css
         */
        russian.selectedProperty().addListener((observable, o, n) -> {
            if (n) {
                LogManager.getLogger().debug("Language selected: Russia");
                english.setSelected(false);
                HElias.getProperties().setLanguage(LanguageLoader.getRussianLanguage());
                try {
                    Parent ROOT = FXMLLoader.getParent("main", FXMLLoader.FXMLType.ROOT, LanguageLoader.getRussianLanguage());
                    Parent settings = (Parent) FXMLLoader.lookupById((JFXTabPane) ROOT, "settings").getContent();
                    ((JFXTabPane) ROOT).getSelectionModel().select(((JFXTabPane) ROOT).getTabs().size() - 1);
                    FXMLLoader.lookupById(FXMLLoader.lookupById(settings, "languages", HBox.class), "russian", JFXRadioButton.class).setSelected(true);
                    Fx.getScene().setRoot(ROOT);
                    ThemeLoader.load(HElias.getProperties().getTheme()).applyTheme(Fx.getScene());
                } catch (IOException e) { ErrorSupport.showErrorPopup(e); }
            }
        });
        english.selectedProperty().addListener((observable, o, n) -> {
            if (n) {
                LogManager.getLogger().debug("Language selected: English");
                russian.setSelected(false);
                HElias.getProperties().setLanguage(LanguageLoader.getEnglishLanguage());
                try {
                    Parent ROOT = FXMLLoader.getParent("main", FXMLLoader.FXMLType.ROOT, LanguageLoader.getEnglishLanguage());
                    Parent settings = (Parent) FXMLLoader.lookupById((JFXTabPane) ROOT, "settings").getContent();
                    ((JFXTabPane) ROOT).getSelectionModel().select(((JFXTabPane) ROOT).getTabs().size() - 1);
                    FXMLLoader.lookupById(FXMLLoader.lookupById(settings, "languages", HBox.class), "english", JFXRadioButton.class).setSelected(true);
                    Fx.getScene().setRoot(ROOT);
                    ThemeLoader.load(HElias.getProperties().getTheme()).applyTheme(Fx.getScene());
                } catch (IOException e) { ErrorSupport.showErrorPopup(e); }
            }
        });
        switch (HElias.getProperties().getTheme()) {
            case DRAGON: enableDragonTheme.setSelected(true); break;
        }
        enableDragonTheme.selectedProperty().addListener(new WrapperChangeListener<Boolean>() {
            @Override
            public void wrapChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (oldValue) return;
                    themes.getChildren().filtered(node -> !node.equals(enableDragonTheme)).forEach(node -> ((JFXRadioButton) node).setSelected(false));
                    HElias.getProperties().setTheme(ThemeLoader.ThemeType.DRAGON);
                    ThemeLoader.loadDragonTheme().applyTheme(Fx.getScene());
                } else if (themes.getChildren().stream().noneMatch(node -> (((JFXRadioButton) node).isSelected()))) {
                    enableDragonTheme.setSelected(true);
                }
            }
        });
        LogManager.getLogger().info("Settings tab was initialized");
    }
}
