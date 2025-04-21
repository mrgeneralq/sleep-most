package me.mrgeneralq.sleepmost.flags.serialization;

import me.mrgeneralq.sleepmost.models.enums.FriendlyNamed;

import java.util.Arrays;
import java.util.stream.Stream;

public class EnumSerialization<E extends Enum<?>> implements IValueSerialization<E> {
    private final Class<E> enumClass;

    public EnumSerialization(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String serialize(E value) {
        if (FriendlyNamed.class.isAssignableFrom(enumClass)) {
            return ((FriendlyNamed) value).friendlyName();
        }
        return value.name();
    }

    @Override
    public E parseValueFrom(Object object) {
        if (this.enumClass.isAssignableFrom(object.getClass())) {
            return enumClass.cast(object);
        }

        if (object instanceof String) {
            String enumName = (String) object;

            Stream<E> stream = Arrays.stream(this.enumClass.getEnumConstants());
            if (FriendlyNamed.class.isAssignableFrom(enumClass)) {
                stream = stream.filter(enumInstance -> ((FriendlyNamed) enumInstance).friendlyName().equalsIgnoreCase(enumName));
            } else {
                stream = stream.filter(enumInstance -> enumInstance.name().equalsIgnoreCase(enumName));
            }

            return stream.findFirst().orElse(null);
        }

        return null;
    }
}
