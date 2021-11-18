package ru.hzerr.config.profile;

import com.google.common.base.Verify;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import ru.hzerr.config.profile.settings.Settings;
import ru.hzerr.modification.state.strategy.ProjectType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Profile extends RecursiveTreeObject<Profile> implements Serializable {

    private transient StringProperty profileName = new SimpleStringProperty();
    private transient ObjectProperty<ProjectType> projectType = new SimpleObjectProperty<>();
    private transient ObjectProperty<Structure> structure = new SimpleObjectProperty<>();
    private transient ObjectProperty<Settings> settings = new SimpleObjectProperty<>();

    public Profile(String profileName, ProjectType projectType, Structure structure, Settings settings) {
        this.profileName.set(profileName);
        this.projectType.set(projectType);
        this.structure.set(structure);
        this.settings.set(settings);
    }

    public StringProperty getProfileNameProperty() { return this.profileName; }
    public ObjectProperty<ProjectType> getProjectTypeProperty() { return this.projectType; }
    public ObjectProperty<Structure> getStructureProperty() { return this.structure; }
    public ObjectProperty<Settings> getSettingsProperty() { return settings; }

    public void rename(String newProfileName) { this.profileName.set(newProfileName); }

    public void deleteProfile() throws IOException { this.structure.getValue().destroyStructure(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Profile)) return false;

        Profile profile = (Profile) o;

        return new EqualsBuilder().append(profileName, profile.profileName)
                .append(projectType, profile.projectType)
                .append(structure, profile.structure)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(profileName)
                .append(projectType)
                .append(structure)
                .toHashCode();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(profileName.getValue());
        oos.writeObject(projectType.getValue());
        oos.writeObject(structure.getValue());
        oos.writeObject(settings.getValue());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        profileName = new SimpleStringProperty((String) ois.readObject());
        projectType = new SimpleObjectProperty<>((ProjectType) ois.readObject());
        structure = new SimpleObjectProperty<>((Structure) ois.readObject());
        settings = new SimpleObjectProperty<>((Settings) ois.readObject());
    }

    public static Profile newProfile(String profileName, ProjectType projectType, Structure structure, Settings settings) {
        return new Profile(
                Verify.verifyNotNull(profileName),
                Verify.verifyNotNull(projectType),
                Verify.verifyNotNull(structure),
                Verify.verifyNotNull(settings)
        );
    }
}
