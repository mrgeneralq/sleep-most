package me.mrgeneralq.sleepmost.models;

import me.mrgeneralq.sleepmost.enums.SleepMostHook;

import java.util.ArrayList;
import java.util.List;

public abstract class Hook {

    private final SleepMostHook sleepMostHook;
   private final List<Hook> dependencies = new ArrayList<>();
   private final List<String> aliases = new ArrayList<>();
   private boolean enabled = false;

   private final String name;

    public Hook(SleepMostHook sleepMostHook, String name) {
        this.sleepMostHook = sleepMostHook;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void addDependency(Hook hook){
        this.dependencies.add(hook);
    }

    public void addAlias(String aliasPlugin){
        this.aliases.add(aliasPlugin);
    }

    public List<Hook> getDependencies(){
        return this.dependencies;
    }

    public SleepMostHook getType(){
        return this.sleepMostHook;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
