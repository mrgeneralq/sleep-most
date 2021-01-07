package me.mrgeneralq.sleepmost.flags.types;

public abstract class StringFlag extends AbstractFlag<String>
{
    public StringFlag(String name, String valueCommandDescription)
    {
        super(name, valueCommandDescription);
    }

    @Override
    public String parseValueFrom(String stringValue)
    {
        return stringValue;
    }
}