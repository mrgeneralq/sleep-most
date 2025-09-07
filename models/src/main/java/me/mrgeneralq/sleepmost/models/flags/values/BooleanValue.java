package me.mrgeneralq.sleepmost.models.flags.values;

public class BooleanValue extends AbstractFlagValue<Boolean>
{
    public BooleanValue(Boolean value)
    {
        super(value);
    }

    @Override
    public String serialize()
    {
        return String.valueOf(get());
    }

    /*@Override
    public Boolean fromString(String value)
    {
        return Boolean.valueOf(value);
    }*/
}
