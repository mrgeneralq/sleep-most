package me.mrgeneralq.sleepmost.services.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.mrgeneralq.sleepmost.services.IBossBarService;
import me.mrgeneralq.sleepmost.services.IConfigService;
import me.mrgeneralq.sleepmost.services.concretes.BossBarService;
import me.mrgeneralq.sleepmost.services.concretes.ConfigService;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind your services here
        // For example:
        // bind(IYourService.class).to(YourServiceImpl.class);


        bind(IBossBarService.class).to(BossBarService.class).in(Singleton.class);
        bind(IConfigService.class).to(ConfigService.class).in(Singleton.class);

    }
}
