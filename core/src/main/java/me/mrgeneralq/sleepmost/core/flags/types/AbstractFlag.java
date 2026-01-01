package me.mrgeneralq.sleepmost.core.flags.types;

import me.mrgeneralq.sleepmost.core.flags.ISleepFlag;
import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.IValueSerialization;
import org.bukkit.World;

public abstract class AbstractFlag<V> implements ISleepFlag<V> {
    private final String name, valueDescription;
    private final V defaultValue;
    private AbstractFlagController<V> controller;
    private final IValueSerialization<V> serialization;

    public AbstractFlag(String name, String valueDescription, AbstractFlagController<V> controller, IValueSerialization<V> serialization, V defaultValue) {
        this.name = name;
        this.valueDescription = valueDescription;
        this.controller = controller;
        this.serialization = serialization;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValueDescription() {
        return this.valueDescription;
    }

    public AbstractFlagController<V> getController() {
        return this.controller;
    }

    @Override
    public V getValueAt(World world) {
        if (this.controller == null) {
            return this.defaultValue;
        }

        return this.controller.getValueAt(world);
    }

    @Override
    public void setValueAt(World world, V value) {
        this.controller.setValueAt(world, value);
    }

    @Override
    public boolean isValidValue(Object value) {
        return this.serialization.parseValueFrom(value) != null;
    }

    @Override
    public IValueSerialization<V> getSerialization() {
        return this.serialization;
    }

    @Override
    public V getDefaultValue() {
        return this.defaultValue;
    }

    public void setController(AbstractFlagController<V> controller) {
        this.controller = controller;
    }
}