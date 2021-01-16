package me.mrgeneralq.sleepmost.flags.serialization;

import java.util.Arrays;

public class EnumSerialization<E extends Enum<E>> implements IValueSerialization<E>
{
    private final Class<E> enumClass;

    public EnumSerialization(Class<E> enumClass)
    {
        this.enumClass = enumClass;
    }

    @Override
    public Object serialize(E value)
    {
        return value.name();
    }

    @Override
    public E parseValueFrom(Object object)
    {
        if(this.enumClass.isAssignableFrom(object.getClass()))
        {
            return enumClass.cast(object);
        }

        if(object instanceof String)
        {
            String enumName = String.valueOf(object);

            return Arrays.stream(this.enumClass.getEnumConstants())
                    .filter(enumInstance -> enumInstance.name().equals(enumName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
