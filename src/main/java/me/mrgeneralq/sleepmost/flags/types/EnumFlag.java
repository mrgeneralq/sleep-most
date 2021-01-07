package me.mrgeneralq.sleepmost.flags.types;

import java.util.Arrays;

public abstract class EnumFlag<E extends Enum<E>> extends AbstractFlag<E>
{
    private final Class<E> enumClass;

    public EnumFlag(String name, String valueCommandDescription, Class<E> enumClass)
    {
        super(name, valueCommandDescription);

        this.enumClass = enumClass;
    }

    @Override
    public E parseValueFrom(String stringValue)
    {
        return Arrays.stream(this.enumClass.getEnumConstants())
                .filter(enumInstance -> enumInstance.name().equals(stringValue))
                .findFirst()
                .orElse(null);
    }
}
