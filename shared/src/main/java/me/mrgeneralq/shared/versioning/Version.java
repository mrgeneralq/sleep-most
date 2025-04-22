package me.mrgeneralq.shared.versioning;

import org.jetbrains.annotations.NotNull;

public class Version {

    private final String version;

    public Version(String version) {
        if(version == null)
            throw new IllegalArgumentException("Version can not be null");
        if(!version.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format");
        this.version = version;
    }

    public final String get() {
        return this.version;
    }

    public boolean isBiggerThan(@NotNull Version other) {

        String[] thisNodes = this.get().split("\\.");
        String[] otherNodes = other.get().split("\\.");

        int length = Math.max(thisNodes.length, otherNodes.length);

        for(int index = 0; index < length; index++) {
            // Get node for each side or null if no node is present at index.
            int thisNode = index < thisNodes.length ? Integer.parseInt(thisNodes[index]) : 0;
            int otherNode = index < otherNodes.length ? Integer.parseInt(otherNodes[index]) : 0;
            if (thisNode > otherNode) return true;
            if (thisNode < otherNode) return false;
        }

        return false;
    }
}