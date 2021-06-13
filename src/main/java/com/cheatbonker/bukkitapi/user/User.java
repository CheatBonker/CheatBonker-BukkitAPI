package com.cheatbonker.bukkitapi.user;

import com.cheatbonker.bukkitapi.module.CheatBonkerModule;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private UUID playerUUID;
    public boolean staffModulesStatus;
    public List<Waypoint> waypointsList;
    public List<CheatBonkerModule> disallowedModules;

    public User(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.staffModulesStatus = false;
        this.waypointsList = new ArrayList<>();
        this.disallowedModules = new ArrayList<>();
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
}
