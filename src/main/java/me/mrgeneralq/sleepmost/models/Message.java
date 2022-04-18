package me.mrgeneralq.sleepmost.models;

public class Message {

    private final String path;
    private final String defaultValue;

    public Message(String path, String defaultValue) {
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
