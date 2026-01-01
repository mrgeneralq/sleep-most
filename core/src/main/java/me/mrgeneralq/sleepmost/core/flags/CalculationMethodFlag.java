package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.core.flags.types.FriendlyNamedEnumFlag;

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
