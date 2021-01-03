package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class NightcycleAnimationFlag implements ISleepFlag<Boolean> {

    private final ISleepFlagService sleepFlagService;

    public NightcycleAnimationFlag(ISleepFlagService sleepFlagService){
        this.sleepFlagService = sleepFlagService;
    }

    @Override
    public String getName() {
        return "nightcycle-animation";
    }

    @Override
    public String getUsage() {
        return "/sleepmost setflag sleep-animation <true|false>";
    }

    @Override
    public boolean isValidValue(String value) {
        return value.equals("true") || value.equals("false");
    }

    @Override
    public FlagType getType() {
        return FlagType.BOOLEAN;
    }

    @Override
    public Boolean getValue(World world) {
    	Object flagValue = sleepFlagService.getFlagValue(world, this.getName());
    	
    	return flagValue == null ? null : (boolean) flagValue;
    }

    @Override
    public void setValue(World world, Boolean value) {
        sleepFlagService.setFlag(world,this.getName(),value);
    }

}
