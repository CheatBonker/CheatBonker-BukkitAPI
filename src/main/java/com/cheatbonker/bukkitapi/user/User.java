package com.cheatbonker.bukkitapi.user;

import com.cheatbonker.bukkitapi.waypoints.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID playerUUID;
    private boolean staffModulesStatus = false;
    public List<Waypoint> waypointsList;

    public User(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.waypointsList = new ArrayList<>();
    }

    public void setStaffModulesStatus(boolean enabled) {
        this.staffModulesStatus = enabled;
    }

    public boolean getStaffModulesStatus() {
        return this.staffModulesStatus;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
}
