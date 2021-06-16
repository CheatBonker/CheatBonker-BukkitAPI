package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.packet.Packet;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;

public class PacketAddWaypoint extends Packet {
    private Waypoint waypoint;

    public PacketAddWaypoint(Waypoint waypoint) {
        this.waypoint = waypoint;
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(0);
        //waypoint information
        this.writeString(this.waypoint.getName());
        this.buf.writeInt(this.waypoint.getX());
        this.buf.writeInt(this.waypoint.getY());
        this.buf.writeInt(this.waypoint.getZ());
        this.writeColor(this.waypoint.getColor());
        //lmao i will add world name later
        this.writeString(this.waypoint.getDimensionName());
    }
}
