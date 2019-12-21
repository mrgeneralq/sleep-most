package me.qintinator.sleepmost.interfaces;

import org.bukkit.World;

public interface IConfigRepository {

    public double getPercentageRequired(World world);
    public boolean containsWorld(World world);
    public String getString(String string);

    public int getCooldown();
    public boolean getMobNoTarget(World world);
    public String getPrefix();
    public void reloadConfig();
    public void addWorld(World world);
    public void removeWorld(World world);
    public void setFlag(World world, String flag, Object value);
}
