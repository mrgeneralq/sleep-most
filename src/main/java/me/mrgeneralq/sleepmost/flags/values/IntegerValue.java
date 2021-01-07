package me.mrgeneralq.sleepmost.flags.values;

public class IntegerValue extends NumericValue<Integer>
{
    public IntegerValue(int value)
    {
        super(value);
    }

    @Override
    public String serialize()
    {
        return String.valueOf(get());
    }
}
