package de.blocki.enhancedplugins.enhancedcoordinates.utils;

import org.bukkit.Location;

public class CoordData {

    private final double x;
    private final double y;
    private final double z;
    private boolean isRounded;

    public CoordData(double x, double y, double z, boolean round) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.isRounded = round;
    }

    public CoordData(Location location, boolean round){
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();

        this.isRounded = round;
    }

    public CoordData(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CoordData(Location location){
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public double getX() {
        return (isRounded ? Math.round(x) : x);
    }

    public double getY() {
        return (isRounded ? Math.round(y) : y);
    }

    public double getZ() {
        return (isRounded ? Math.round(z) : z);
    }

    public void setRounded(boolean rounded) {
        isRounded = rounded;
    }
}
