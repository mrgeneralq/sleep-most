package me.mrgeneralq.sleepmost.core.flags;

import me.mrgeneralq.sleepmost.core.enums.SleepersOrAllType;
import me.mrgeneralq.sleepmost.core.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.core.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.core.flags.types.FriendlyNamedEnumFlag;

public class SkipMsgAudienceFlag extends FriendlyNamedEnumFlag<SleepersOrAllType> {

    public SkipMsgAudienceFlag(AbstractFlagController<SleepersOrAllType> controller) {
        super(
                "skip-msg-audience",
                "<sleepers|all>",
                controller,
                new EnumSerialization<>(SleepersOrAllType.class),
                SleepersOrAllType.values(),
                SleepersOrAllType.ALL
        );
    }
}
