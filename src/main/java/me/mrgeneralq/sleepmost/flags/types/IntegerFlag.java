package me.mrgeneralq.sleepmost.flags;

public abstract class IntegerFlag extends AbstractFlag<Integer>
{
    public IntegerFlag(String name, String usage)
    {
        super(name, usage);
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