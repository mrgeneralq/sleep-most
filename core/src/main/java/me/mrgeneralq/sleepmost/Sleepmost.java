package me.mrgeneralq.sleepmost;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.mrgeneralq.sleepmost.bootstrapper.Bootstrapper;
import me.mrgeneralq.sleepmost.modules.PluginModule;
import org.bukkit.plugin.java.JavaPlugin;

public class Sleepmost extends JavaPlugin {

	@Override
	public void onEnable() {
		Injector injector = Guice.createInjector(new PluginModule(this));
		Bootstrapper bootstrapper = injector.getInstance(Bootstrapper.class);
		bootstrapper.bootstrap();
	}
}
