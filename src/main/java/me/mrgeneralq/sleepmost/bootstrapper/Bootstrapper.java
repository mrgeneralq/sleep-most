package me.mrgeneralq.sleepmost.bootstrapper;

import com.google.inject.Inject;

public class Bootstrapper {

    private final EventBootstrapper eventBootstrapper;
    private final CommandBootstrapper commandBootstrapper;

    @Inject
    public Bootstrapper(
            EventBootstrapper eventListenerBootstrapper,
            CommandBootstrapper commandBootstrapper,
    ) {
        this.eventBootstrapper = eventListenerBootstrapper;
        this.commandBootstrapper = commandBootstrapper;
    }

    public void bootstrap() {
        System.out.println("Bootstrapping the application...");

        eventBootstrapper.bootstrap();
        commandBootstrapper.bootstrap();



    }
}
