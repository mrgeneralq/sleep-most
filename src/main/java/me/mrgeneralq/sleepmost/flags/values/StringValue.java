package me.mrgeneralq.sleepmost.flags.values;

public class StringValue extends AbstractFlagValue<String>
{
    public StringValue(String value)
    {
        super(value);
    }

    @Override
    public String serialize()
    {
        return this.value;
    }

    /*@Override
    public String fromConfigValue(String value)
    {
        return value;
    }*/
}
