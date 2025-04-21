package me.mrgeneralq.sleepmost.modules;

import com.google.inject.AbstractModule;
import me.mrgeneralq.sleepmost.Sleepmost;

public class PluginModule extends AbstractModule {

    private final Sleepmost plugin;

    public PluginModule(Sleepmost plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(Sleepmost.class).toInstance(plugin);

    }


}
