package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ru.hzerr.HElias;
import ru.hzerr.controller.actions.downloader.McSkillSwitchContextHandler;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.network.DownloadException;
import ru.hzerr.exception.network.WebSiteNotWorkingException;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.file.HFile;
import ru.hzerr.util.Downloader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Download controller.
 */
public class DownloadController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ChoiceBox<JFXButton> choiceProjects;
    @FXML private JFXButton download;
    @FXML private JFXButton checkUpdate;
    @FXML private JFXButton downloadAndSetDefaultInSettings;

    private static final String MC_SKILL_DOWNLOAD_JAR_URL = "https://mcskill.net/McSkill.jar";
    private static final String MYTHICAL_WORLD_DOWNLOAD_JAR_URL = "https://mythicalworld.su/launch/MythicalWorld.zip";

    @FXML
    public void initialize() {
        final JFXButton MC_SKILL = new JFXButton();
        // McSkillSwitchContextHandler
        MC_SKILL.setOnAction(event -> {
            try {
                BaseFile mcSkillJarFile = Downloader.download(MC_SKILL_DOWNLOAD_JAR_URL, HDirectory.createTempDirectory("mc-skill"));
                if (HElias.getProperties().checkExistsProfileWithName(mcSkillJarFile.getName())) {
                    download.setDisable(true);
                    downloadAndSetDefaultInSettings.setDisable(true);
                    long currentChecksum = HElias.getProperties().getProfileByName(mcSkillJarFile.getName()).getStructureProperty().getValue().getCommercialProjectJarFile().checksum();
                    long targetChecksum = mcSkillJarFile.checksum();
                    if (currentChecksum == targetChecksum) {
                    }
                    return;
                }
                download.setDisable(false);
                downloadAndSetDefaultInSettings.setDisable(false);
//                download.setOnAction(event -> );
            } catch (IOException | DownloadException | WebSiteNotWorkingException e) {
                ErrorSupport.showErrorPopup(e);
            }
        });
        final JFXButton MYTHICAL_WORLD = new JFXButton();
        choiceProjects.getItems().addAll(MC_SKILL, MYTHICAL_WORLD);
    }
}
