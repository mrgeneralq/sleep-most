package me.mrgeneralq.sleepmost.flags.serialization;

public class StringSerialization implements IValueSerialization<String>
{
    public static final StringSerialization INSTANCE = new StringSerialization();

    @Override
    public String parseValueFrom(Object object)
    {
        return String.valueOf(object);
    }
}
