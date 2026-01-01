package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.enums.SleepersOrAllType;
import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.core.flags.types.FriendlyNamedEnumFlag;

public class PhantomResetAudienceFlag extends FriendlyNamedEnumFlag<SleepersOrAllType> {

    public PhantomResetAudienceFlag(AbstractFlagController<SleepersOrAllType> controller) {
        super(
                "phantom-reset-audience",
                "<sleepers|all>",
                controller,
                new EnumSerialization<>(SleepersOrAllType.class),
                SleepersOrAllType.values(),
                SleepersOrAllType.ALL
        );
    }
}
