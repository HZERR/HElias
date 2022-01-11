package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXToggleButton;
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
    @FXML private Label testProjectNameLabel;
    @FXML private Label launcherNameLabel;
    @FXML private Label versionLauncherLabel;
    @FXML private Label buildLauncherLabel;
    @FXML private JFXToggleButton enableExpertMode;
    @FXML private Label buildNameState;
    @FXML private Label launcherNameState;
    @FXML private Label launcherVersion;
    @FXML private Label launcherBuild;
    @FXML private Label pathToInstalledProject;
    @FXML private Label pathToJava;

    @FXML
    void onChangeBuildName(ActionEvent event) {
        ChoiceTextController choiceTextController = new ChoiceTextController();
        choiceTextController.setOldValue(buildProjectNameShouldFillLabel.getText());
        choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.name.project.build"));
        choiceTextController.setError(resources.getString("popup.choice.text.error.change.name.project.build"));
        choiceTextController.setPattern(Pattern.compile("^[\\w-.]+\\.jar$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS));
        choiceTextController.setOnFinished(selectedValue -> {
            HElias.getProperties().setProjectTestName(selectedValue);
            buildProjectNameShouldFillLabel.setText(selectedValue);
        });
        choiceTextController.setRoot(root);
        try {
            FXMLLoader.showPopup("choice-text", resources, choiceTextController);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
    }

    @FXML
    void onChangeLauncherBuild(ActionEvent event) {
        ChoiceTextController choiceTextController = new ChoiceTextController();
        choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.build.launcher"));
        choiceTextController.setError(resources.getString("popup.choice.text.error.change.build.launcher"));
        choiceTextController.setOldValue(buildLauncherShouldFillLabel.getText());
        choiceTextController.setPattern(null);
        choiceTextController.setOnFinished(selectedValue -> {
            HElias.getProperties().setLauncherBuild(selectedValue);
            buildLauncherShouldFillLabel.setText(selectedValue);
        });
        choiceTextController.setRoot(root);
        try {
            FXMLLoader.showPopup("choice-text", resources, choiceTextController);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
    }

    @FXML
    void onChangeLauncherName(ActionEvent event) {
        ChoiceTextController choiceTextController = new ChoiceTextController();
        choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.name.launcher"));
        choiceTextController.setError(resources.getString("popup.choice.text.error.change.name.launcher"));
        choiceTextController.setOldValue(launcherNameShouldFillLabel.getText());
        choiceTextController.setPattern(null);
        choiceTextController.setOnFinished(selectedValue -> {
            HElias.getProperties().setLauncherName(selectedValue);
            launcherNameShouldFillLabel.setText(selectedValue);
        });
        choiceTextController.setRoot(root);
        try {
            FXMLLoader.showPopup("choice-text", resources, choiceTextController);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
    }

    @FXML
    void onChangeLauncherVersion(ActionEvent event) {
        ChoiceTextController choiceTextController = new ChoiceTextController();
        choiceTextController.setTitle(resources.getString("popup.choice.text.title.change.version.launcher"));
        choiceTextController.setError(resources.getString("popup.choice.text.error.change.version.launcher"));
        choiceTextController.setOldValue(versionLauncherShouldFillLabel.getText());
        choiceTextController.setPattern(null);
        choiceTextController.setOnFinished(selectedValue -> {
            HElias.getProperties().setLauncherVersion(selectedValue);
            versionLauncherShouldFillLabel.setText(selectedValue);
        });
        choiceTextController.setRoot(root);
        try {
            FXMLLoader.showPopup("choice-text", resources, choiceTextController);
        } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
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

        openFolderButton.setOnAction(event -> {
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
            case DARK: enableDarkTheme.setSelected(true); break;
            case PURPLE_V1: enablePurpleThemeV1.setSelected(true); break;
            case PURPLE_V2: enablePurpleThemeV2.setSelected(true); break;
        }
        enableDarkTheme.selectedProperty().addListener((observable, o, n) -> {
            if (n) {
                if (o) return;
                themes.getChildren().filtered(node -> !node.equals(enableDarkTheme)).forEach(node -> ((JFXRadioButton) node).setSelected(false));
                HElias.getProperties().setTheme(ThemeLoader.ThemeType.DARK);
                ThemeLoader.loadDarkTheme().applyTheme(Fx.getScene());
            } else if (themes.getChildren().stream().noneMatch(node -> (((JFXRadioButton) node).isSelected()))) {
                enableDarkTheme.setSelected(true);
            }
        });
        enablePurpleThemeV1.selectedProperty().addListener((observable, o, n) -> {
            if (n) {
                if (o) return;
                themes.getChildren().filtered(node -> !node.equals(enablePurpleThemeV1)).forEach(node -> ((JFXRadioButton) node).setSelected(false));
                HElias.getProperties().setTheme(ThemeLoader.ThemeType.PURPLE_V1);
                ThemeLoader.loadPurpleThemeV1().applyTheme(Fx.getScene());
            } else if (themes.getChildren().stream().noneMatch(node -> (((JFXRadioButton) node).isSelected()))) {
                enablePurpleThemeV1.setSelected(true);
            }
        });
        enablePurpleThemeV2.selectedProperty().addListener((observable, o, n) -> {
            if (n) {
                if (o) return;
                themes.getChildren().filtered(node -> !node.equals(enablePurpleThemeV2)).forEach(node -> ((JFXRadioButton) node).setSelected(false));
                HElias.getProperties().setTheme(ThemeLoader.ThemeType.PURPLE_V2);
                ThemeLoader.loadPurpleThemeV2().applyTheme(Fx.getScene());
            } else if (themes.getChildren().stream().noneMatch(node -> (((JFXRadioButton) node).isSelected()))) {
                enablePurpleThemeV2.setSelected(true);
            }
        });
        LogManager.getLogger().info("Settings tab was initialized");
    }
}
