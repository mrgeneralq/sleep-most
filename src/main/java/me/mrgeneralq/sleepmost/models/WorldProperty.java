package me.mrgeneralq.sleepmost.models;

public class WorldProperty {

    private boolean insomniaEnabled = false;

    public boolean isInsomniaEnabled() {
        return insomniaEnabled;
    }

    public void setInsomniaEnabled(boolean insomniaEnabled) {
        this.insomniaEnabled = insomniaEnabled;
    }



    public static WorldProperty getNewProperty(){
        return new WorldProperty();
    }


}
