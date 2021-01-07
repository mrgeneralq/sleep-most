package me.mrgeneralq.sleepmost.flags.types;

public abstract class StringFlag extends AbstractFlag<String>
{
    public StringFlag(String name, String valueDescription)
    {
        super(name, valueDescription);
    }

    @Override
    public String parseValueFrom(String stringValue)
    {
        return stringValue;
    }
}