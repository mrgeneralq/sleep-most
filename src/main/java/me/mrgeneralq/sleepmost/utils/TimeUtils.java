package me.mrgeneralq.sleepmost.utils;

import com.google.common.base.Strings;

public class TimeUtils {

    public static String getTimeStringByTicks(double timeInTicks){
        int hours = (int) ((Math.floor(timeInTicks / 1000) + 6) % 24);
        int minutes = (int) Math.floor((timeInTicks % 1000)/1000 * 60);
        return Strings.padStart(String.valueOf(hours),2, '0') + ":" +  Strings.padStart(String.valueOf(minutes),2, '0');


    }


}
