package me.mrgeneralq.sleepmost.flags.types;

import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.IValueSerialization;

public class EnumFlag<E extends Enum<E>> extends AbstractFlag<E>
{
    private final Class<E> enumClass;

    public EnumFlag(String name, String valueDescription, AbstractFlagController<E> controller, IValueSerialization<E> serialization, Class<E> enumClass)
    {
        super(name, valueDescription, controller, serialization);

        this.enumClass = enumClass;
    }
    public Class<E> getEnumClass()
    {
        return this.enumClass;
    }

    /*@Override
    public boolean isValidValue(Object object)
    {
        String enumName = String.valueOf(object);

        return Arrays.stream(this.enumClass.getEnumConstants())
                .anyMatch(enumInstance -> enumInstance.name().equals(enumName));
    }*/
}
