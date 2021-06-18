package com.cheatbonker.bukkitapi;

import com.cheatbonker.bukkitapi.dimension.Dimension;
import com.cheatbonker.bukkitapi.event.CheatBonkerRegisterEvent;
import com.cheatbonker.bukkitapi.module.CheatBonkerModule;
import com.cheatbonker.bukkitapi.packet.Packet;
import com.cheatbonker.bukkitapi.packet.server.*;
import com.cheatbonker.bukkitapi.user.User;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

//my ocd needs this
@SuppressWarnings("all")
public class CheatBonkerAPI implements Listener {
    //list of players running cheatbonker
    private final List<User> playersRunningCheatBonker;
    //plugin instance, used when sending packets to players
    private final Plugin plugin;
    //handles all incoming packets
    private final CBNetHandler netHandler;

    //initialize crap and register plugin channel and events
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

    public boolean setDisallowedModules(Player player, CheatBonkerModule... modulesToDisable) {
        User user = this.getUserFromPlayer(player);
        if (user == null) {
            System.err.println("Couldn't change " + player.getName() + "'s disabled modules because they aren't using CheatBonker");
            return false;
        }
        //cba to google how to turn this into a list
        user.disallowedModules = Arrays.stream(modulesToDisable).collect(Collectors.toList());

        this.sendPacket(new PacketDisallowedModules(modulesToDisable), player);
        return true;
    }

    public boolean setDisallowedModules(Player player, List<CheatBonkerModule> modulesToDisable) {
        User user = this.getUserFromPlayer(player);
        if (user == null) {
            System.err.println("Couldn't change " + player.getName() + "'s disabled modules because they aren't using CheatBonker");
            return false;
        }

        user.disallowedModules = modulesToDisable;
        this.sendPacket(new PacketDisallowedModules(modulesToDisable), player);
        return true;
    }

    public boolean setStaffModulesStatus(Player player, boolean status) {
        User user = this.getUserFromPlayer(player);
        if (user == null) {
            System.err.println("Couldn't change " + player.getName() + "'s staff modules status because they aren't using CheatBonker");
            return false;
        }

        user.staffModulesStatus = status;
        this.sendPacket(new PacketStaffModulesStatus(status), player);
        return true;
    }

    public boolean addWaypoint(Player playerToAddWaypoint, Waypoint waypoint) {
        User user = this.getUserFromPlayer(playerToAddWaypoint);
        if (user == null) {
            System.err.println("Couldn't add a waypoint to " + playerToAddWaypoint.getName() + " because they aren't using CheatBonker");
            return false;
        }

        user.waypointsList.add(waypoint);
        this.sendPacket(new PacketAddWaypoint(waypoint), playerToAddWaypoint);
        return true;
    }

    public boolean addWaypoint(Player playerToAddWaypoint, String name, Dimension dimension, int x, int y, int z, Color color) {
        User user = this.getUserFromPlayer(playerToAddWaypoint);
        if (user == null) {
            System.err.println("Couldn't add a waypoint to " + playerToAddWaypoint.getName() + " because they aren't using CheatBonker");
            return false;
        }

        Waypoint waypoint = new Waypoint(name, dimension, x, y, z, color);
        user.waypointsList.add(waypoint);
        this.sendPacket(new PacketAddWaypoint(waypoint), playerToAddWaypoint);
        return true;
    }

    public boolean removeWaypoint(Player playerToRemoveWaypoint, Waypoint waypointToRemove) {
        User user = this.getUserFromPlayer(playerToRemoveWaypoint);
        if (user == null) {
            System.err.println("Couldn't remove a waypoint from " + playerToRemoveWaypoint.getName() + " because they aren't using CheatBonker");
            return false;
        }

        user.waypointsList.remove(waypointToRemove);
        this.sendPacket(new PacketRemoveWaypoint(waypointToRemove), playerToRemoveWaypoint);
        return true;
    }

    public boolean removeWaypoint(Player playerToRemoveWaypoint, String waypointName, Dimension dimension) {
        User user = this.getUserFromPlayer(playerToRemoveWaypoint);
        if (user == null) {
            System.err.println("Couldn't remove a waypoint from " + playerToRemoveWaypoint.getName() + " because they aren't using CheatBonker");
            return false;
        }
        String dimensionName = dimension.getDimensionName();

        for (int i = 0; i < user.waypointsList.size(); i++) {
            Waypoint waypoint = user.waypointsList.get(i);
            if (waypoint.getName().equals(waypointName) && waypoint.getDimensionName().equals(dimensionName)) {
                user.waypointsList.remove(waypoint);
            }
        }
        this.sendPacket(new PacketRemoveWaypoint(waypointName, dimensionName), playerToRemoveWaypoint);
        return true;
    }

    public boolean sendNotification(Player playerToSendNotification, String title, String description, long length) {
        User user = this.getUserFromPlayer(playerToSendNotification);
        if (user == null) {
            System.err.println("Couldn't send a notification to " + playerToSendNotification.getName() + " because they aren't using CheatBonker");
            return false;
        }

        this.sendPacket(new PacketSendNotification(title, description, length), playerToSendNotification);
        return true;
    }

    private User getUserFromPlayer(Player player) {
        for (User user : this.playersRunningCheatBonker) {
            if (user.getPlayerUUID() == player.getUniqueId()) {
                return user;
            }
        }
        return null;
    }

    private User getUserFromUUID(UUID uuid) {
        for (User user : this.playersRunningCheatBonker) {
            if (user.getPlayerUUID() == uuid) {
                return user;
            }
        }
        return null;
    }

    private void addPlayerToRunningCheatBonker(Player player) {
        this.playersRunningCheatBonker.add(new User(player.getUniqueId()));
    }

    private void addPlayerToRunningCheatBonker(UUID uuid) {
        this.playersRunningCheatBonker.add(new User(uuid));
    }

    private void removePlayerFromRunningCheatBonker(Player player) {
        this.playersRunningCheatBonker.remove(this.getUserFromPlayer(player));
    }

    private void removePlayerFromRunningCheatBonker(UUID uuid) {
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

    public boolean doesPlayerHaveStaffModules(Player player) {
        User user = this.getUserFromPlayer(player);
        if (user == null) {
            return false;
        }
        return user.staffModulesStatus;
    }

    public boolean doesPlayerHaveStaffModules(UUID uuid) {
        User user = this.getUserFromUUID(uuid);
        if (user == null) {
            return false;
        }
        return user.staffModulesStatus;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerQuit(PlayerQuitEvent event) {
        this.removePlayerFromRunningCheatBonker(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerRegisterChannel(PlayerRegisterChannelEvent event) {
        if ("CheatBonker".equals(event.getChannel()) == true) {
            this.addPlayerToRunningCheatBonker(event.getPlayer());
            CheatBonkerRegisterEvent registerEvent = new CheatBonkerRegisterEvent(event.getPlayer());
            Bukkit.getServer().getPluginManager().callEvent(registerEvent);
        }
    }

    private void sendPacket(Packet packet, Player player) {
        packet.write();
        player.sendPluginMessage(this.plugin, "CheatBonker", packet.buf.array());
    }
}
