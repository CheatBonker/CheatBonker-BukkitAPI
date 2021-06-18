package com.cheatbonker.bukkitapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CheatBonkerRegisterEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public CheatBonkerRegisterEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}