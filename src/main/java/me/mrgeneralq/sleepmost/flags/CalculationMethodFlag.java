package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class CalculationMethodFlag implements ISleepFlag<String> {


    private final ISleepFlagService sleepFlagService;
    private String flagName = "calculation-method";
    //TODO add message service
    private String flagUsage = "/sm setflag calculation-method <percentage|players>";
    private FlagType flagType = FlagType.STRING;

    public CalculationMethodFlag(ISleepFlagService sleepFlagService) {
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
        String enumName = String.format("%s%s", value.toUpperCase(), "_REQUIRED");
        try{
            SleepCalculationType.valueOf(enumName);
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
    public String getValue(World world) {
        Object flagValue = this.sleepFlagService.getFlagValue(world, this.getFlagName());
        return flagValue == null ? null : (String) flagValue;
    }

    @Override
    public void setValue(World world, String value) {
        this.sleepFlagService.setFlag(world, this.flagName, value);
    }
}
