package me.mrgeneralq.sleepmost.flags.serialization;

import me.mrgeneralq.sleepmost.enums.SleepCalculationType;

public class SleepCalculationTypeSerialization extends EnumSerialization<SleepCalculationType>
{
    public static final SleepCalculationTypeSerialization INSTANCE = new SleepCalculationTypeSerialization();

    public SleepCalculationTypeSerialization()
    {
        super(SleepCalculationType.class);
    }

    @Override
    public Object serialize(SleepCalculationType value)
    {
        return getDisplayName(value);
    }

    @Override
    public String getDisplayName(SleepCalculationType value)
    {
        return value.name().substring(0, value.name().indexOf('_')).toLowerCase();
    }

    @Override
    public SleepCalculationType parseValueFrom(Object object)
    {
        Object serialized;

        if(object instanceof String)
            serialized = String.format("%s_REQUIRED", ((String) object).toUpperCase());
        else
            serialized = object;

        return super.parseValueFrom(serialized);
    }
}
