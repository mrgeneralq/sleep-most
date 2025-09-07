package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.models.flags.types.FriendlyNamedEnumFlag;

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
