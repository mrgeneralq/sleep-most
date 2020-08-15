package me.qintinator.sleepmost.flags;

import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import me.qintinator.sleepmost.interfaces.ISleepFlagService;
import me.qintinator.sleepmost.statics.Bootstrapper;
import org.bukkit.World;

public class UseExemptFlag implements ISleepFlag<Boolean> {

	private final Bootstrapper bootstrapper;
	private final ISleepFlagService sleepFlagService;

	public UseExemptFlag(){
		this.bootstrapper = Bootstrapper.getBootstrapper();
		this.sleepFlagService = bootstrapper.getSleepFlagService();
	}

	@Override
	public String getFlagName() {
		return "use-exempt";
	}

	@Override
	public String getFlagUsage() {
		return "/sleepmost setflag use-exempt <true|false>";
	}

	@Override
	public boolean isValidValue(String value) {
		return value.equals("true")||value.equals("false");
	}

	@Override
	public FlagType getFlagType() {
		return FlagType.BOOLEAN;
	}

	@Override
	public Boolean getValue(World world) {
		if(sleepFlagService.getFlag(world, getFlagName()) == null)
			return null;

		return (Boolean) sleepFlagService.getFlag(world, getFlagName());
	}

	@Override
	public void setValue(World world, Boolean value) {
	}

}
