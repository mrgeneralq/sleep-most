package me.mrgeneralq.sleepmost.models;

import me.mrgeneralq.sleepmost.enums.HookType;

import java.util.ArrayList;
import java.util.List;

public abstract class Hook {

    private final HookType hookType;
   private final List<Hook> dependencies = new ArrayList<>();
   private final List<String> aliases = new ArrayList<>();

   private final String name;

    public Hook(HookType hookType ,String name) {
        this.hookType = hookType;
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

    public HookType getType(){
        return this.hookType;
    }

}
