package me.mrgeneralq.sleepmost.flags;
import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.types.EnumFlag;

public class CalculationMethodFlag extends EnumFlag<SleepCalculationType>
{
    public CalculationMethodFlag()
    {
        super("calculation-method", "<percentage|players>", SleepCalculationType.class);
    }

    @Override
    public boolean isValidValue(String stringValue)
    {
        String enumName = String.format("%s_REQUIRED", stringValue.toUpperCase());

        return super.isValidValue(enumName);
    }
}
