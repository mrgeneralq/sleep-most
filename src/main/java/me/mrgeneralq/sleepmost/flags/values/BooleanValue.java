package me.mrgeneralq.sleepmost.flags.values;

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
    public Boolean fromConfigValue(String value)
    {
        return Boolean.valueOf(value);
    }*/
}
