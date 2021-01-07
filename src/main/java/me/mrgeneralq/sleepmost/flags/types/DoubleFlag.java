package me.mrgeneralq.sleepmost.flags.types;

public class DoubleFlag extends AbstractFlag<Double>
{
    public DoubleFlag(String name, String valueCommandDescription)
    {
        super(name, valueCommandDescription);
    }

    @Override
    public Double parseValueFrom(String stringValue)
    {
        try {
            return Double.parseDouble(stringValue);
        }
        catch(NumberFormatException exception) {
            return null;
        }
    }
}
