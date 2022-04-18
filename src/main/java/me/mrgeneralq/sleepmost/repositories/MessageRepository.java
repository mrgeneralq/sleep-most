package me.mrgeneralq.sleepmost.repositories;

import me.mrgeneralq.sleepmost.interfaces.IRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements IRepository<String, String> {


    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public void remove(String key) {

    }

    private String getMessageFromConfig(Object object){

        List<String> stringList = new ArrayList<>();

        Object messageSection = stringList;

        return "";
    }
}
