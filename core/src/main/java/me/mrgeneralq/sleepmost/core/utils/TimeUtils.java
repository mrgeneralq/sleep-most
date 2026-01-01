package me.mrgeneralq.sleepmost.core.utils;

public class TimeUtils {
    public static String getTimeStringByTicks(double timeInTicks){
        int hours = (int) ((Math.floor(timeInTicks / 1000) + 6) % 24);
        int minutes = (int) Math.floor((timeInTicks % 1000) / 1000 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }
}
