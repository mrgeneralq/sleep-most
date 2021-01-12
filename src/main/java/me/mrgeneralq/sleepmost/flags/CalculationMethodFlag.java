package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.EnumFlag;
import me.mrgeneralq.sleepmost.flags.serialization.SleepCalculationTypeSerialization;

public class CalculationMethodFlag extends EnumFlag<SleepCalculationType>
{
    public CalculationMethodFlag(AbstractFlagController<SleepCalculationType> controller)
    {
        super("calculation-method", "<percentage|players>", controller, SleepCalculationTypeSerialization.INSTANCE, SleepCalculationType.class);
    }
}
