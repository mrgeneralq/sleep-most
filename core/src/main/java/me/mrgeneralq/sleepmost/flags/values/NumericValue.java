package me.mrgeneralq.sleepmost.flags.values;

import java.util.function.Predicate;

public abstract class NumericValue<N extends Number> extends AbstractFlagValue<N>
{
    public NumericValue(N value)
    {
        super(value);
    }

    @Override
    public String serialize()
    {
        //calling the toString() method of Number
        return this.value.toString();
    }
    public boolean isBetween(double min, double max)
    {
        return is(value -> value >= min && value <= max);
    }
    public boolean is(Predicate<Double> predicate)
    {
        double doubleValue = this.value.doubleValue();

        return predicate.test(doubleValue);
    }
}
