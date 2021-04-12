package eu.gillespie.timewarriorcontrol;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Getter
public enum Permission {
    READ("twjc.read"),
    WRITE("twjc.write"),
    DELETE("twjc.delete");

    String configKey;

    Permission(String configKey) {
        this.configKey = configKey;
    }

    public String getConfig(String pathAppendix) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File("twjc.properties")));
        return properties.getProperty(this.configKey + "." + pathAppendix);
    }

    public boolean canAllow() {
        try {
            return Boolean.parseBoolean(this.getConfig("canAllow"));
        } catch (IOException e) {
            return false;
        }
    }
}
