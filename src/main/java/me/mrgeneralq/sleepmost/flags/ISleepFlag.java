package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;

public interface ISleepFlag<V>
{
    String getName();
    String getCommandUsage();
    boolean isValidValue(String stringValue);
    V parseValueFrom(String stringValue);

    //Controller data
    AbstractFlagController<V> getController();
    void setController(AbstractFlagController<V> controller);
}
