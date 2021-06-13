package com.cheatbonker.bukkitapi.dimension;

public enum Dimension {
    OVERWORLD("Overworld"),
    NETHER("Nether"),
    THE_END("The End");

    private final String dimensionName;

    Dimension(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionName() {
        return dimensionName;
    }
}
