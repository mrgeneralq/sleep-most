package me.mrgeneralq.sleepmost.flags.types;

public abstract class IntegerFlag extends AbstractFlag<Integer>
{
    public IntegerFlag(String name, String valueCommandDescription)
    {
        super(name, valueCommandDescription);
    }

    @Override
    public Integer parseValueFrom(String stringValue)
    {
        try {
            return Integer.parseInt(stringValue);
        }
        catch(NumberFormatException exception) {
            return null;
        }
    }
}