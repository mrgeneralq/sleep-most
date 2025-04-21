package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.SleepersOrAllType;
import me.mrgeneralq.sleepmost.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.flags.types.FriendlyNamedEnumFlag;

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
