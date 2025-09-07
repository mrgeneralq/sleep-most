package me.mrgeneralq.sleepmost.models.flags;

import me.mrgeneralq.sleepmost.models.enums.SleepersOrAllType;
import me.mrgeneralq.sleepmost.models.flags.controllers.AbstractFlagController;
import me.mrgeneralq.sleepmost.models.flags.serialization.EnumSerialization;
import me.mrgeneralq.sleepmost.models.flags.types.FriendlyNamedEnumFlag;

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
