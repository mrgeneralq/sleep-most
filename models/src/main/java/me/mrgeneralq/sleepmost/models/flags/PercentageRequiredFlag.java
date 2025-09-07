package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.types.DoubleFlag;
import me.mrgeneralq.sleepmost.models.flags.serialization.DoubleSerialization;

public class PercentageRequiredFlag extends DoubleFlag
{
    public PercentageRequiredFlag(AbstractFlagController<Double> controller) {
        super("percentage-required", "<0.1 - 1>", controller, 0.5);
    }

    @Override
    public boolean isValidValue(Object value) {
        Double percentages = DoubleSerialization.INSTANCE.parseValueFrom(value);

        if(percentages == null) {
            return false;
        }
        return percentages >= 0 && percentages <= 1;
    }
}
