package me.mrgeneralq.sleepmost.flags.serialization;

public class IntegerSerialization implements IValueSerialization<Integer>
{
    public static final IntegerSerialization INSTANCE = new IntegerSerialization();

    @Override
    public Integer parseValueFrom(Object object)
    {
        //if the value is retrieved from the config
        if(object instanceof Integer)
            return (Integer) object;

        //if the value is retrieved by the /sm setflag command
        else if(object instanceof String)
            return parseFromString((String) object);

        return null;
    }

    protected static Integer parseFromString(String text)
    {
        try {
            return Integer.parseInt(text);
        }
        catch(NumberFormatException exception) {
            return null;
        }
    }
}
