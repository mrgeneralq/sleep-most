package me.mrgeneralq.sleepmost.core.flags.values;

public class DoubleValue extends NumericValue<Double>
{
    public DoubleValue(Double value)
    {
        super(value);
    }

    @Override
    public String serialize()
    {
        return String.valueOf(get());
    }

    /*@Override
    public Double fromString(String value)
    {
        return Double.valueOf(value);
    }*/
}
