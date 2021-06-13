package com.cheatbonker.bukkitapi.waypoints;

import com.cheatbonker.bukkitapi.dimension.Dimension;

import java.awt.*;

public class Waypoint {
    private String name, dimensionName;
    private int x, y, z;
    private Color color;

    public Waypoint(String name, Dimension dimension, int x, int y, int z, Color color) {
        this.name = name;
        this.dimensionName = dimension.getDimensionName();
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getDimensionName() {
        return dimensionName;
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
