package me.mrgeneralq.sleepmost.repositories.concretes;

import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IRepository;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class MessageRepository implements IRepository<String, String> {

    private final static String MESSAGE_CONFIG_NAME = "messages.yml";

    public MessageRepository() {
        this.createConfig();
    }

    private File file;
    private FileConfiguration config;


    @Override
    public String get(String path) {
        return getRandomMessage(this.config.get(path));
    }

    @Override
    public void set(String path, String value) {
        this.config.set(path, value);
    }

    @Override
    public boolean exists(String path) {
        return this.config.contains(path);
    }

    @Override
    public void remove(String path) {
        this.config.set(path, null);
        this.saveConfig();
    }

    private String getRandomMessage(Object object){

        if(object instanceof List){
            List<String> messageList = (List<String>) object;
            if(messageList.size() > 0)
            return messageList.get(new Random().nextInt(messageList.size()));
        }

        if(object instanceof String)
            return (String) object;

        return "";
    }


    private void createConfig(){

        this.file = new File(Sleepmost.getInstance().getDataFolder(), MESSAGE_CONFIG_NAME);
        if(!this.file.exists()){
            this.file.getParentFile().mkdirs();
            Sleepmost.getInstance().saveResource(MESSAGE_CONFIG_NAME, false);
        }

        this.config = new YamlConfiguration();
        this.loadConfig();
    }

    public void loadConfig(){
        try{
            this.config.load(this.file);
        }catch (IOException |InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public void saveConfig(){
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
