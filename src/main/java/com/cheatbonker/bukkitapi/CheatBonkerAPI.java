package com.cheatbonker.bukkitapi;

import com.cheatbonker.bukkitapi.module.CheatBonkerModule;
import com.cheatbonker.bukkitapi.packet.Packet;
import com.cheatbonker.bukkitapi.packet.server.PacketAddWaypoint;
import com.cheatbonker.bukkitapi.packet.server.PacketDisallowedModules;
import com.cheatbonker.bukkitapi.packet.server.PacketRemoveWaypoint;
import com.cheatbonker.bukkitapi.packet.server.PacketStaffModulesStatus;
import com.cheatbonker.bukkitapi.user.User;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheatBonkerAPI implements Listener {
    private final List<User> playersRunningCheatBonker;
    private final Plugin plugin;
    public CBNetHandler netHandler;

    public CheatBonkerAPI(Plugin plugin) {
        this.netHandler = new CBNetHandler();
        this.playersRunningCheatBonker = new ArrayList<>();
        this.plugin = plugin;
        Messenger messenger = Bukkit.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, "CheatBonker");
        messenger.registerIncomingPluginChannel(plugin, "CheatBonker", new PluginMessageListener() {
            @Override
            public void onPluginMessageReceived(String channel, Player player, byte[] message) {
                //read byte array to ByteBuf
                ByteBuf byteBuf = Unpooled.wrappedBuffer(message);
                //get the packet id, then find the corresponding packet
                int packetId = byteBuf.readInt();
                for (int i : Packet.incomingPacketMap.keySet()) {
                    if (packetId == i) {
                        //do the packet thingys
                        Class<? extends Packet> packet = Packet.incomingPacketMap.get(i);
                        try {
                            Packet packetInstance = packet.newInstance();
                            packetInstance.read(byteBuf);
                            packetInstance.handle(CheatBonkerAPI.this.netHandler);
                        } catch (InstantiationException | IllegalAccessException e) {
                            //prob wont happen
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                System.err.println("Couldn't find packet for id " + packetId + "!");
            }
        });
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void setDisallowedModules(Player player, CheatBonkerModule... modulesToDisable) {
        this.sendPacket(new PacketDisallowedModules(modulesToDisable), player);
    }

    public void setDisallowedModules(Player player, List<CheatBonkerModule> modulesToDisable) {
        this.sendPacket(new PacketDisallowedModules(modulesToDisable), player);
    }

    public void setStaffModulesStatus(Player player, boolean status) {
        this.sendPacket(new PacketStaffModulesStatus(status), player);
    }

    /**
     * Sends a waypoint packet to the specified player
     * @param playerToAddWaypoint to send the waypoint packet
     * @param waypoint instance of Waypoint, a class that contains all waypoint information
     */
    public void addWaypoint(Player playerToAddWaypoint, Waypoint waypoint) {
        this.sendPacket(new PacketAddWaypoint(waypoint), playerToAddWaypoint);
    }

    //gets removed by waypoint name
    public void removeWaypoint(Player playerToRemoveWaypoint, Waypoint waypointToRemove) {
        this.sendPacket(new PacketRemoveWaypoint(waypointToRemove), playerToRemoveWaypoint);
    }

    public void removeWaypoint(Player playerToRemoveWaypoint, String waypointName) {
        this.sendPacket(new PacketRemoveWaypoint(waypointName), playerToRemoveWaypoint);
    }

    public User getUserFromPlayer(Player player) {
        for (User user : this.playersRunningCheatBonker) {
            if (user.getPlayerUUID() == player.getUniqueId()) {
                return user;
            }
        }
        return null;
    }

    public User getUserFromUUID(UUID uuid) {
        for (User user : this.playersRunningCheatBonker) {
            if (user.getPlayerUUID() == uuid) {
                return user;
            }
        }
        return null;
    }

    public void addPlayerToRunningCheatBonker(Player player) {
        this.playersRunningCheatBonker.add(new User(player.getUniqueId()));
    }

    public void addPlayerToRunningCheatBonker(UUID uuid) {
        this.playersRunningCheatBonker.add(new User(uuid));
    }

    public void removePlayerFromRunningCheatBonker(Player player) {
        this.playersRunningCheatBonker.remove(this.getUserFromPlayer(player));
    }

    public void removePlayerFromRunningCheatBonker(UUID uuid) {
        this.playersRunningCheatBonker.remove(this.getUserFromUUID(uuid));
    }

    public boolean isPlayerRunningCheatBonker(Player player) {
        for (User user : this.playersRunningCheatBonker) {
            if (user.getPlayerUUID().equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerRunningCheatBonker(UUID uuid) {
        for (User user : this.playersRunningCheatBonker) {
            if (user.getPlayerUUID().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.removePlayerFromRunningCheatBonker(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRegisterChannel(PlayerRegisterChannelEvent event) {
        if ("CheatBonker".equals(event.getChannel()) == true) {
            System.out.println(event.getPlayer().getName() + " is using ceheratobnker!!1111!");
            this.addPlayerToRunningCheatBonker(event.getPlayer());
        }
    }

    private void sendPacket(Packet packet, Player player) {
        packet.write();
        player.sendPluginMessage(this.plugin, "CheatBonker", packet.buf.array());
    }
}
