package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.DoubleSerialization;
import me.mrgeneralq.sleepmost.core.flags.types.DoubleFlag;

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
