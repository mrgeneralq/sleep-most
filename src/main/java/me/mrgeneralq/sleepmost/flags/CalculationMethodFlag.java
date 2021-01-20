package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.EnumFlag;
import me.mrgeneralq.sleepmost.flags.serialization.SleepCalculationTypeSerialization;

public class CalculationMethodFlag extends EnumFlag<SleepCalculationType>
{
    public CalculationMethodFlag(AbstractFlagController<SleepCalculationType> controller) {
        super(
                "calculation-method",
                "<players|percentage>",
                controller,
                SleepCalculationTypeSerialization.INSTANCE,
                SleepCalculationType.class,
                SleepCalculationType.PERCENTAGE_REQUIRED
        );
    }

    @Override
    public String getDisplayName(SleepCalculationType value) {
        String enumName = value.name();

        return enumName.substring(0, enumName.indexOf('_')).toLowerCase();
    }
}
