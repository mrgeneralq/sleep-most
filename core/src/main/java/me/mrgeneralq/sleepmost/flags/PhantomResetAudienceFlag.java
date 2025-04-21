package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.models.enums.SleepersOrAllType;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.flags.types.FriendlyNamedEnumFlag;

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
