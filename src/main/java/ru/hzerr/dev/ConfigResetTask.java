package ru.hzerr.dev;

import org.apache.commons.configuration2.ex.ConfigurationException;
import ru.hzerr.HElias;
import ru.hzerr.file.BaseFile;

import java.io.IOException;

final class ConfigResetTask {

    public static void main(String[] args) throws IOException, ConfigurationException {
        BaseFile config = HElias.getProperties().getConfigFile();
        if (config.exists()) {
            if (config.delete()) {
                HElias.getProperties().init();
                System.out.println("Success!");
            } else
                System.err.println("Config file can't be deleted");
        } else
            System.err.println("Config file not found");
    }
}
