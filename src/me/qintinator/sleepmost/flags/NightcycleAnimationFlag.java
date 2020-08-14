package me.qintinator.sleepmost.flags;
import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepFlagService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.World;

public class NightcycleAnimationFlag implements ISleepFlag<Boolean> {

    private final ISleepFlagService sleepFlagService;

    public NightcycleAnimationFlag(){
        this.sleepFlagService = Bootstrapper.getBootstrapper().getSleepFlagService();
    }

    @Override
    public String getFlagName() {
        return "nightcycle-animation";
    }

    @Override
    public String getFlagUsage() {
        return "/sleepmost setflag sleep-animation <true|false>";
    }

    @Override
    public boolean isValidValue(String value) {
        return value.equals("true") || value.equals("false");
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.BOOLEAN;
    }

    @Override
    public Boolean getValue(World world) {
    	Object flagValue = sleepFlagService.getFlag(world, this.getFlagName());
    	
    	return flagValue == null ? null : (boolean) flagValue;
    }

    @Override
    public void setValue(World world, Boolean value) {
        sleepFlagService.setFlag(world,this.getFlagName(),value);
    }

}
