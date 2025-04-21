package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.models.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.flags.types.FriendlyNamedEnumFlag;

public class CalculationMethodFlag extends FriendlyNamedEnumFlag<SleepCalculationType> {

    public CalculationMethodFlag(AbstractFlagController<SleepCalculationType> controller) {
        super(
                "calculation-method",
                "<players|percentage>",
                controller,
                new EnumSerialization<>(SleepCalculationType.class),
                SleepCalculationType.values(),
                SleepCalculationType.PERCENTAGE_REQUIRED
        );
    }
}
