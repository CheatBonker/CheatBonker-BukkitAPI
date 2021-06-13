package com.cheatbonker.bukkitapi.waypoints;

import java.awt.*;

public class Waypoint {
    private String name;
    private int x, y, z;
    private Color color;

    public Waypoint(String name, int x, int y, int z, Color color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Color getColor() {
        return color;
    }
}
