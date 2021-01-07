package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;

public abstract class AbstractFlag<V> implements ISleepFlag<V> {

    private final String name;
    private final String commandUsage;
    private AbstractFlagController<V> controller;

    public AbstractFlag(String name, String valueCommandDescription)
    {
        this.name = name;
        this.commandUsage = String.format("/sleepmost setflag %s %s", name, valueCommandDescription);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getCommandUsage()
    {
        return this.commandUsage;
    }

    @Override
    public boolean isValidValue(String stringValue)
    {
        return parseValueFrom(stringValue) != null;
    }

    @Override
    public AbstractFlagController<V> getController()
    {
        return this.controller;
    }

    @Override
    public void setController(AbstractFlagController<V> controller)
    {
        this.controller = controller;
    }
}