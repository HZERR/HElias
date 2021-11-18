package ru.hzerr.dev;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import ru.hzerr.HElias;
import ru.hzerr.collections.list.ArrayHList;
import ru.hzerr.config.Properties;

import java.io.IOException;
import java.lang.reflect.Field;

public class PrependPropertyTask {

    private static final String key = "profiles";
    private static final Object value = new ArrayHList<>();

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, ConfigurationException, IOException {
        Properties properties = HElias.getProperties();
        properties.init();
        Field configField = Properties.class.getDeclaredField("CONFIG");
        configField.setAccessible(true);
        PropertiesConfiguration configuration = (PropertiesConfiguration) configField.get(properties);
        if (!configuration.containsKey(key)) {
            configuration.addProperty(key, value);
            // check getter what is no throws
            configuration.getList(Object.class, key);
            System.out.println("The property " + key + " was successfully added!");
        } else
            System.err.println("The property " + key + " has not been added!");
    }
}
