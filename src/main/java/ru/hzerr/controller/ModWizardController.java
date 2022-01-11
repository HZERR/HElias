package ru.hzerr.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ru.hzerr.HElias;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.config.PropertyNames;
import ru.hzerr.config.listener.StringEventListener;
import ru.hzerr.config.profile.Profile;
import ru.hzerr.controller.popup.ChoiceFileController;
import ru.hzerr.exception.ErrorSupport;
import ru.hzerr.exception.modification.ModsFolderNotFoundException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.file.HFile;
import ru.hzerr.loaders.FXMLLoader;
import ru.hzerr.log.LogManager;
import ru.hzerr.modification.Project;
import ru.hzerr.modification.util.Launcher;
import ru.hzerr.stream.HStream;
import ru.hzerr.util.Schedulers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// TODO: 04.01.2022 REWRITE
public class ModWizardController {

    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private AnchorPane root;
    @FXML private JFXListView<String> modList;
    @FXML private JFXButton deleteMod;
    @FXML private JFXButton addMod;
    @FXML private JFXButton start;

    private HStream<BaseFile> modStream;
    private final HList<BaseFile> modsToBeRemoved = new ArrayHList<>();
    private final HList<BaseFile> modsToBeAdded = new ArrayHList<>();
    private BaseDirectory modsDir;

    public void initialize() {
        modList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        StringEventListener listener = new StringEventListener(PropertyNames.PATH_TO_INSTALLED_PROJECT) {

            @Override
            public void onRun(String newValue) {
                BaseDirectory installedProjectDir = new HDirectory(newValue);
                if (installedProjectDir.exists()) {
                    try {
                        modsDir = installedProjectDir.findDirectories(dir -> dir.getName().equals("mods"))
                                .findFirst().orElseThrow(ModsFolderNotFoundException::new);
                        modStream = modsDir.findFiles(file -> file.getExtension().equals("jar"));
                        modStream.forEach(mod -> modList.getItems().add(mod.getBaseName()));
                        modsToBeRemoved.clear();
                    } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
                }
            }
        };
        HElias.getProperties().addListener(listener.fire(HElias.getProperties().getPathToInstalledProject()));
        deleteMod.setOnAction(event -> {
            if (modStream != null) {
                modStream.forEach(mod -> {
                    if (mod.getBaseName().equals(modList.getSelectionModel().getSelectedItem())) {
                        modsToBeRemoved.add(mod);
                        Platform.runLater(() -> modList.getItems().remove(mod.getBaseName()));
                    }
                });

                modsToBeAdded.forEach(mod -> {
                    if (mod.getBaseName().equals(modList.getSelectionModel().getSelectedItem())) {
                        modsToBeAdded.remove(mod);
                        Platform.runLater(() -> modList.getItems().remove(mod.getBaseName()));
                    }
                });
            }
        });
        // multiple choice
        addMod.setOnAction(event -> {
            try {
                FileChooser projectChooser = new FileChooser();
                projectChooser.setTitle(resources.getString("file.chooser.title.path.to.mods.add"));
                ChoiceFileController choiceFileController = new ChoiceFileController();
                choiceFileController.setExplorer(projectChooser);
                choiceFileController.setValue("");
                choiceFileController.setOnFinished(selectedValue -> {
                    modsToBeAdded.add(new HFile(selectedValue));
                    modList.getItems().add(new HFile(selectedValue).getBaseName());
                });
                choiceFileController.setRoot(root);
                FXMLLoader.showPopup("choice-file", resources, choiceFileController);
            } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
        });
        start.setOnAction(event -> {
            try {
                Optional<Profile> defaultProfile = HElias.getProperties().getDefaultProfile();
                if (!defaultProfile.isPresent()) ErrorSupport.showWarningPopup(resources.getString("tab.launch.master.warning.popup.no.such.default.profile.title"), resources.getString("tab.launch.master.warning.popup.no.such.default.profile.message"));
                final HFile originalProjectJarFile = new HFile(defaultProfile.get().getStructureProperty().getValue().getCommercialProjectJarFile().getLocation());
                BaseDirectory newRoot = HElias.getProperties().getProjectsDir().getSubDirectory(String.valueOf(originalProjectJarFile.checksum()));
                Project project = null; // TODO: 21.12.2021 GO TO PROFILE
                if (newRoot.notExists()) {
                    throw new FileNotFoundException("The project was not modified");
                } else project = Project.getProject(originalProjectJarFile, newRoot, HElias.getProperties().getProjectTestName());
                Launcher.create().apply(project.getBuild().getLocation());
            } catch (IOException e) { ErrorSupport.showErrorPopup(e); }
            if (!modsToBeAdded.isEmpty() || !modsToBeRemoved.isEmpty()) {
                ModCheckerTask CHECKER = ModCheckerTask.newTask();
                CHECKER.setModsDir(modsDir);
                CHECKER.setModsMostAdded(modsToBeAdded);
                CHECKER.setModsMostRemoved(modsToBeRemoved);
                try {
                    CHECKER.start();
                    LogManager.getLogger().info("Mod checker task was started");
                } catch (IOException io) { ErrorSupport.showErrorPopup(io); }
            }
        });
        LogManager.getLogger().info("ModWizard tab was initialized");
    }

    @FXML
    void onAddMod(ActionEvent event) {

    }

    @FXML
    void onDeleteMod(ActionEvent event) {

    }

    @FXML
    void onStart(ActionEvent event) {

    }

    private static class ModCheckerTask {

        private BaseDirectory mods; // all mods
        private HList<BaseFile> modsMostRemoved;
        private HList<BaseFile> modsMostAdded; // with random location

        private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        private ModCheckerTask() {
        }

        public void setModsDir(BaseDirectory mods) { this.mods = mods; }
        public void setModsMostAdded(HList<BaseFile> modsMostAdded) { this.modsMostAdded = modsMostAdded; }
        public void setModsMostRemoved(HList<BaseFile> modsMostRemoved) { this.modsMostRemoved = modsMostRemoved; }

        public void start() throws IOException {
            modsMostRemoved.forEach(mod -> {
                try {
                    mod.delete();
                } catch (IOException io) { throw new RuntimeException(io); }
            });
            modsMostAdded.forEach(mod -> {
                try {
                    mod.copyToDirectory(mods);
                } catch (IOException io) { throw new RuntimeException(io); }
            });
            Schedulers.register(service);
            service.scheduleAtFixedRate(() -> {
                if (modsMostRemoved.isEmpty() && modsMostAdded.isEmpty()) service.shutdownNow();
                modsMostRemoved.forEach(mod -> {
                    if (mod.exists()) {
                        try {
                            mod.delete();
                            modsMostRemoved.remove(mod);
                        } catch (IOException io) { throw new RuntimeException(io); }
                    }
                });
                modsMostAdded.forEach(mod -> {
                    if (mods.getSubFile(mod.getBaseName()).notExists()) {
                        try {
                            mod.copyToDirectory(mods);
                            modsMostAdded.remove(mod);
                        } catch (IOException io) { throw new RuntimeException(io); }
                    }
                });
            }, 0, 2, TimeUnit.SECONDS);
//            WatchService watchService = FileSystems.getDefault().newWatchService();
//            final Map<WatchKey, Path> keys = new HashMap<>();
//            modsMostAdded.map(BaseFile::asPath).forEach(path -> {
//                try {
//                    keys.put(path.register(watchService, StandardWatchEventKinds.ENTRY_DELETE), path);
//                } catch (IOException io) { throw new RuntimeException(io); }
//            });
//            modsMostRemoved.map(BaseFile::asPath).forEach(path -> {
//                try {
//                    keys.put(path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE), path);
//                } catch (IOException io) { throw new RuntimeException(io); }
//            });
//            Schedulers.register(service);
//            service.scheduleAtFixedRate(() -> {
//                WatchKey key;
//                try {
//                    while ((key = watchService.take()) != null) {
//                        Path path = keys.get(key);
//                        for (WatchEvent<?> event : key.pollEvents()) {
//                            SessionLogManager.getManager().getLogger().info("Watch service event: " + event.kind().name());
//                            if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind()))
//                            if (Files.exists(path)) {
//                                Files.delete(path);
//                            } else
//                                new HFile(path.toFile().getAbsolutePath()).create();
//                        }
//                        key.reset();
//                    }
//                } catch (IOException | InterruptedException e) { throw new RuntimeException(e); }
//            }, 0, 2, TimeUnit.SECONDS);
        }

        public static ModCheckerTask newTask() { return new ModCheckerTask(); }
    }
}
