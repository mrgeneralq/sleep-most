package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class PlayersRequiredFlag implements ISleepFlag<Integer> {


    private final ISleepFlagService sleepFlagService;
    private String flagName = "players-required";
    //TODO add message service
    private String flagUsage = "type /sm setflag players-required <amount>";
    private FlagType flagType = FlagType.INTEGER;

    public PlayersRequiredFlag(ISleepFlagService sleepFlagService) {
        this.sleepFlagService = sleepFlagService;
    }


    @Override
    public String getFlagName() {
        return this.flagName;
    }

    @Override
    public String getFlagUsage() {
        return this.flagUsage;
    }

    @Override
    public boolean isValidValue(String value) {
        try {
             Integer.parseInt(value);
             return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public FlagType getFlagType() {
        return this.flagType;
    }

    @Override
    public Integer getValue(World world) {
        return (Integer) this.sleepFlagService.getFlagValue(world, this.flagName);
    }

    @Override
    public void setValue(World world, Integer value) {

    }
}
