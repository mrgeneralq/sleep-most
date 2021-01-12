package me.mrgeneralq.sleepmost.flags.serialization;

public class BooleanSerialization implements IValueSerialization<Boolean>
{
    public static final BooleanSerialization INSTANCE = new BooleanSerialization();

    @Override
    public Boolean parseValueFrom(Object object)
    {
        if(object instanceof Boolean)
            return (Boolean) object;

        if(object instanceof String)
        {
            String text = (String) object;

            if(text.equalsIgnoreCase("true"))
                return true;

            if(text.equalsIgnoreCase("false"))
                return false;
        }
        return null;
    }
}
