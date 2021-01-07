package me.mrgeneralq.sleepmost.flags.types;

public class BooleanFlag extends AbstractFlag<Boolean>
{
    public BooleanFlag(String name, String valueCommandDescription)
    {
        super(name, valueCommandDescription);
    }
    public BooleanFlag(String name)
    {
        this(name, "<true|false>");
    }

    @Override
    public Boolean parseValueFrom(String stringValue)
    {
        if(stringValue.equalsIgnoreCase("true"))
            return true;

        if(stringValue.equalsIgnoreCase("false"))
            return false;

        return null;
    }
}
