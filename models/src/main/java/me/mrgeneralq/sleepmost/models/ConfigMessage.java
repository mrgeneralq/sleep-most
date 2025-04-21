package me.mrgeneralq.sleepmost.models;

public class ConfigMessage {

    private final String path;
    private final String defaultValue;

    public ConfigMessage(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
