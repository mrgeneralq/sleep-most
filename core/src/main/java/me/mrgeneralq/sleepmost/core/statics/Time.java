package me.mrgeneralq.sleepmost.core.statics;

public class Time {

    public static int MID_NIGHT = 18000;
    public static int NOON = 6000;


    public static boolean aroundMidNight(double time){
        return time > MID_NIGHT && time < MID_NIGHT + 25;
    }

    public static boolean aroundNoon(double time){
        return time > NOON && time < NOON + 25;
    }

    public static boolean aroundTime(double originTime, double currentTime){
        return currentTime > originTime && currentTime < originTime + 25;
    }

}
