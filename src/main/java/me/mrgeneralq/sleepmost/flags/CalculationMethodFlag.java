package me.mrgeneralq.sleepmost.flags.list;
import me.mrgeneralq.sleepmost.enums.SleepCalculationType;
import me.mrgeneralq.sleepmost.flags.StringFlag;

//TODO: implement an EnumFlag class
public class CalculationMethodFlag extends StringFlag
{
    public CalculationMethodFlag()
    {
        super("calculation-method", "<percentage|players>");
    }

    @Override
    public boolean isValidValue(String value)
    {
        String enumName = String.format("%s%s", value.toUpperCase(), "_REQUIRED");

        try{
            SleepCalculationType.valueOf(enumName);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
