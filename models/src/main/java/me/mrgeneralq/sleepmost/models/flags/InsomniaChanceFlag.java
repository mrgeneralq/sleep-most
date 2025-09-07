package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.serialization.DoubleSerialization;
import me.mrgeneralq.sleepmost.models.flags.types.DoubleFlag;

public class InsomniaChanceFlag extends DoubleFlag
{
    public InsomniaChanceFlag(AbstractFlagController<Double> controller) {
        super("insomnia-chance", "<0.1 - 1>", controller, 0.0);
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
