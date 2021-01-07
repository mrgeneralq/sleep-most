package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.flags.types.DoubleFlag;

public class PercentageRequiredFlag extends DoubleFlag
{
    public PercentageRequiredFlag()
    {
        super("percentage-required", "<0.1 - 1>");
    }

    @Override
    public boolean isValidValue(String stringValue)
    {
        if(!super.isValidValue(stringValue))
            return false;

        double value = parseValueFrom(stringValue);

        return value >= 0 && value <= 1;

        //return parseValueFrom(stringValue).isBetween(0, 1);
    }
}
