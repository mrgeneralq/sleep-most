package me.mrgeneralq.sleepmost.models.flags.values;

public class EnumValue<E extends Enum<E>> extends AbstractFlagValue<E>
{
    private final Class<E> enumClass;

    public EnumValue(E value, Class<E> enumClass)
    {
        super(value);

        this.enumClass = enumClass;
    }

    @Override
    public String serialize()
    {
        return this.value.name();
    }

    /*@Override
    public E fromString(String textValue)
    {
        return Arrays.stream(this.enumClass.getEnumConstants())
                .filter(e -> e.name().equals(textValue))
                .findFirst()
                .get();
    }*/
}
