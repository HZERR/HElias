package ru.hzerr.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.SerializationUtils;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.collections.list.HList;
import ru.hzerr.exception.config.GroupNotFoundException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.log.SessionLogManager;

import java.io.IOException;
import java.io.Serializable;

/**
 * The class is responsible for serializing java objects other than String or Enum
 */
public class ObjectProperties {

    // key -> file with bytes
    private PropertiesConfiguration info;
    // info file and files with bytes
    private final BaseDirectory serializedObjectsDirectory;
    private final BaseFile infoFile;

    public ObjectProperties(BaseDirectory configDirectory) {
        serializedObjectsDirectory = configDirectory.getSubDirectory("objects");
        infoFile = serializedObjectsDirectory.getSubFile("HEliasSerializedInfoFile.properties");
    }

    public void saveToGroup(String groupName, String key, Serializable serializable) throws IOException {
        BaseDirectory groupDirectory = serializedObjectsDirectory.createSubDirectory(groupName);
        BaseFile target = groupDirectory.createSubFile(key + ".helias");
        SerializationUtils.serialize(serializable, target.openOutputStream());
    }

    public void removeFromGroup(String groupName, String... keys) throws IOException {
        BaseDirectory groupDirectory = serializedObjectsDirectory.getSubDirectory(groupName);
        if (groupDirectory.notExists()) throw new GroupNotFoundException(groupName);
        for (String key: keys) {
            BaseFile target = groupDirectory.getSubFile(key + ".helias");
            if (target.exists()) target.delete();
        }
    }

    public <T> T loadFromGroup(String groupName, String key, T defaultValue) throws IOException {
        BaseDirectory groupDirectory = serializedObjectsDirectory.getSubDirectory(groupName);
        if (groupDirectory.notExists()) throw new GroupNotFoundException(groupName);
        BaseFile target = groupDirectory.getSubFile(key + ".helias");
        if (target.exists()) return SerializationUtils.deserialize(target.openInputStream());
        else return defaultValue;
    }

    @SuppressWarnings("unchecked")
    public <T> HList<T> loadFromGroup(String groupName, T defaultValue, String... keys) throws IOException {
        BaseDirectory groupDirectory = serializedObjectsDirectory.getSubDirectory(groupName);
        if (groupDirectory.notExists()) throw new GroupNotFoundException(groupName);
        HList<T> result = new ArrayHList<>();
        for (String key: keys) {
            BaseFile target = groupDirectory.getSubFile(key + ".helias");
            if (target.exists()) {
                result.add((T) SerializationUtils.deserialize(target.openInputStream()));
            } else
                result.add(defaultValue);
        }

        return result;
    }

    public <T> HList<T> loadAllFromGroup(String groupName) throws IOException {
        BaseDirectory groupDirectory = serializedObjectsDirectory.getSubDirectory(groupName);
        if (groupDirectory.notExists()) throw new GroupNotFoundException(groupName);
        HList<T> result = new ArrayHList<>(); // maybe copyOnWriteArrayList ???
        groupDirectory.getFiles().forEach(target -> result.add((T) SerializationUtils.deserialize(target.openInputStream())));
        return result;
    }

    public boolean checkKeyExistsInGroup(String groupName, String key) {
        BaseDirectory groupDirectory = serializedObjectsDirectory.getSubDirectory(groupName);
        if (groupDirectory.notExists()) throw new GroupNotFoundException(groupName);
        return groupDirectory.getSubFile(key + ".helias").exists();
    }

    public boolean checkKeyNotFoundInGroup(String groupName, String key) {
        BaseDirectory groupDirectory = serializedObjectsDirectory.getSubDirectory(groupName);
        if (groupDirectory.notExists()) throw new GroupNotFoundException(groupName);
        return groupDirectory.getSubFile(key + ".helias").notExists();
    }

    public boolean isEmptyGroup(String groupName) {
        return serializedObjectsDirectory.getSubDirectory(groupName).isEmpty();
    }

    public boolean checkExistsGroup(String groupName) {
        return serializedObjectsDirectory.getSubDirectory(groupName).exists();
    }

    public boolean checkGroupNotFound(String groupName) {
        return serializedObjectsDirectory.getSubDirectory(groupName).notExists();
    }

    public void createGroup(String groupName) throws IOException {
        serializedObjectsDirectory.createSubDirectory(groupName);
    }

    public void save(String key, Serializable serializable) throws IOException {
        info.setProperty(key, key + ".helias");
        BaseFile serializedObjectFile = serializedObjectsDirectory.createSubFile(key + ".helias");
        SerializationUtils.serialize(serializable, serializedObjectFile.openOutputStream());
    }

    public <T> T load(Class<T> clazz, String key, T defaultValue) throws IOException {
        if (info.containsKey(key)) {
            BaseFile serializedObjectFile = serializedObjectsDirectory.getSubFile(info.getString(key));
            return SerializationUtils.deserialize(serializedObjectFile.openInputStream());
        } else return defaultValue;
    }

    public void init() throws IOException, ConfigurationException {
        SessionLogManager.getManager().getLogger().info("Initialization of the ObjectProperties");
        serializedObjectsDirectory.create();
        infoFile.create();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setFile(infoFile.asIOFile())
                                .setThrowExceptionOnMissing(true)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(';'))
                                .setIncludesAllowed(true));
        builder.setAutoSave(true);
        info = builder.getConfiguration();
        createGroup("profiles");
    }
}
