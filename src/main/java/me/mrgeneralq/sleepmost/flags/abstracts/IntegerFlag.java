package me.mrgeneralq.sleepmost.flags.abstracts;

import me.mrgeneralq.sleepmost.abstracts.AbstractFlag;

public abstract class IntegerFlag extends AbstractFlag<Integer> {

    public IntegerFlag(String name, String usage) {
        super(name, usage);
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
