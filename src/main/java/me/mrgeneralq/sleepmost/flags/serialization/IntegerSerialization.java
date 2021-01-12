package me.mrgeneralq.sleepmost.flags.serialization;

public class IntegerSerialization implements IValueSerialization<Integer>
{
    public static final IntegerSerialization INSTANCE = new IntegerSerialization();

    @Override
    public Integer parseValueFrom(Object object)
    {
        if(object instanceof Integer)
            return (Integer) object;

        if(object instanceof String)
        {
            try
            {
                return Integer.parseInt((String) object);
            }
            catch(NumberFormatException exception){
                return null;
            }
        }
        return null;
    }
}
