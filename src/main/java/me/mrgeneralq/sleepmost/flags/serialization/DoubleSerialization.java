package me.mrgeneralq.sleepmost.flags.serialization;

public class DoubleSerialization implements IValueSerialization<Double>
{
    public static final DoubleSerialization INSTANCE = new DoubleSerialization();

    @Override
    public Double parseValueFrom(Object object)
    {
        if(object instanceof Double)
            return (Double) object;

        if(object instanceof String)
        {
            try
            {
                return Double.parseDouble((String) object);
            }
            catch(NumberFormatException exception){
                return null;
            }
        }
        return null;
    }
}
