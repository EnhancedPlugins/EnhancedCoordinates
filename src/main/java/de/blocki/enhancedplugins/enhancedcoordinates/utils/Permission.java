package de.blocki.enhancedplugins.enhancedcoordinates.utils;

public enum Permission {

    DEFAULT("enhancedcoordinates.use"),
    SELF("enhancedcoordinates.self"),
    BROADCAST("enhancedcoordinates.broadcast"),
    SEND("enhancedcoordinates.send"),
    TELEPORT("enhancedcoordinates.teleport"),
    PLUGIN_STAR("enhancedcoordinates.*"),
    STAR("*");

    private final String text;

    Permission(String text){
        this.text = text;
    }

    public String toString() { return text; }

}
