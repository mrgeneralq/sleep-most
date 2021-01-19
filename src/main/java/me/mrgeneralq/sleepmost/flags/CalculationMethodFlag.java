package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.types.EnumFlag;
import me.mrgeneralq.sleepmost.flags.serialization.SleepCalculationTypeSerialization;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class CalculationMethodFlag extends EnumFlag<SleepCalculationType>
{
    public CalculationMethodFlag(AbstractFlagController<SleepCalculationType> controller) {
        super("calculation-method", createOptionsList(), controller, SleepCalculationTypeSerialization.INSTANCE, SleepCalculationType.class);
    }

    @Override
    public String getDisplayName(SleepCalculationType value) {
        return staticGetDisplayName(value);
    }

    private static String createOptionsList() {
        return Arrays.stream(SleepCalculationType.values())
                .map(type -> staticGetDisplayName(type))
                .collect(joining("|", "<", ">"));
    }

    private static String staticGetDisplayName(SleepCalculationType value) {
        String enumName = value.name();

        return enumName.substring(0, enumName.indexOf('_')).toLowerCase();
    }
}
