package me.mrgeneralq.sleepmost.services;

import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.CalculationMethodFlag;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.IFlagsRepository;
import me.mrgeneralq.sleepmost.interfaces.IHookService;
import me.mrgeneralq.sleepmost.repositories.FlagsRepository;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class UpdateFlagValueTest {

    private World world;
    private IFlagsRepository flagsRepository;

    @Before
    public void setup() {
        world = Mockito.mock(World.class);
        flagsRepository = new FlagsRepository(Mockito.mock(IHookService.class) ,Mockito.mock(IConfigRepository.class));
    }


    public void calculationMethodFlagTest() {
        // Default value should be PERCENTAGE_REQUIRED, as nothing is set yet.
        CalculationMethodFlag calculationFlag = flagsRepository.getCalculationMethodFlag();
        assertEquals(SleepCalculationType.PERCENTAGE_REQUIRED, calculationFlag.getValueAt(world));

        // Update the value and then check if it actually updated.
        calculationFlag.setValueAt(world, SleepCalculationType.PERCENTAGE_REQUIRED);
        assertEquals(SleepCalculationType.PERCENTAGE_REQUIRED, calculationFlag.getValueAt(world));

        // Check if serialization method produces the expected result.
        String friendlyName = (String) calculationFlag.getSerialization().serialize(SleepCalculationType.PERCENTAGE_REQUIRED);
        assertEquals(friendlyName, SleepCalculationType.PERCENTAGE_REQUIRED.friendlyName());

        // Check if parsing from a string produces the expected result for an invalid option.
        SleepCalculationType invalidParsingTest = calculationFlag.getSerialization().parseValueFrom("invalid-value.does-not-parse");
        assertNull(invalidParsingTest);

        // Check if parsing from a string produces the expected result for a valid option.
        SleepCalculationType validParsingTest = calculationFlag.getSerialization().parseValueFrom("players");
        assertEquals(validParsingTest, SleepCalculationType.PLAYERS_REQUIRED);
    }
}
