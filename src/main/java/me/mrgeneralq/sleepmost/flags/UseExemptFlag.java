package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

public class UseExemptFlag implements ISleepFlag<Boolean> {

	private final ISleepFlagService sleepFlagService;

	public UseExemptFlag(ISleepFlagService sleepFlagService){
		this.sleepFlagService = sleepFlagService;
	}

	@Override
	public String getName() {
		return "use-exempt";
	}

	@Override
	public String getUsage() {
		return "/sleepmost setflag use-exempt <true|false>";
	}

	@Override
	public boolean isValidValue(String value) {
		return value.equals("true")||value.equals("false");
	}

	@Override
	public FlagType getType() {
		return FlagType.BOOLEAN;
	}

	@Override
	public Boolean getValue(World world) {
		if(sleepFlagService.getFlagValue(world, getName()) == null)
			return null;

		return (Boolean) sleepFlagService.getFlagValue(world, getName());
	}

	@Override
	public void setValue(World world, Boolean value) {
	}

}
