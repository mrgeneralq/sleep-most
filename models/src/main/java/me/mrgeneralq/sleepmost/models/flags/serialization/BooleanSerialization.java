package me.mrgeneralq.sleepmost.models.flags.serialization;

public class BooleanSerialization implements IValueSerialization<Boolean>
{
    public static final BooleanSerialization INSTANCE = new BooleanSerialization();

    @Override
    public Boolean parseValueFrom(Object object)
    {
        if(object instanceof Boolean)
            return (Boolean) object;

        if(object instanceof String)
            return parseFromString((String) object);

        return null;
    }

    protected static Boolean parseFromString(String text)
    {
        if(text.equalsIgnoreCase("true"))
            return true;

        else if(text.equalsIgnoreCase("false"))
            return false;

        return null;
    }
}
