package me.mrgeneralq.sleepmost.models.flags.values;

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

    /*@Override
    public static Integer fromString(String textValue)
    {
        return Integer.valueOf(textValue);
    }*/
}
