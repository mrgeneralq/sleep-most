package me.mrgeneralq.sleepmost.core.flags.serialization;

public class DoubleSerialization implements IValueSerialization<Double>
{
    public static final DoubleSerialization INSTANCE = new DoubleSerialization();

    @Override
    public Double parseValueFrom(Object object)
    {
        if(object instanceof Double)
            return (Double) object;

        else if(object instanceof String)
            return parseFromString((String) object);

        return null;
    }

    protected static Double parseFromString(String text)
    {
        try {
            return Double.parseDouble(text);
        }
        catch(NumberFormatException exception) {
            return null;
        }
    }
}
