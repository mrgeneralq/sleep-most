package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.Sleepmost;
import me.mrgeneralq.sleepmost.interfaces.IRepository;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        return getRandomMessage(this.config.getString(path));
    }

    @Override
    public void set(String path, String value) {
        this.config.set(path, value);
        this.saveConfig();
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

        List<String> stringList = new ArrayList<>();

        Object messageSection = stringList;

        if(messageSection instanceof List){
            List<String> messageList = (List<String>) messageSection;
            return messageList.get(new Random(messageList.stream().count()).nextInt());
        }

        if(messageSection instanceof String)
            return (String) messageSection;

        return "";
    }


    private void createConfig(){

        this.file = new File(Sleepmost.getInstance().getDataFolder(), MESSAGE_CONFIG_NAME);
        if(!this.file.exists()){
            this.file.getParentFile().mkdirs();
            Sleepmost.getInstance().saveResource(MESSAGE_CONFIG_NAME, false);
        }

        this.config = new YamlConfiguration();
        try{
            this.config.load(this.file);
        }catch (IOException |InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    private void saveConfig(){
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
