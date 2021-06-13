package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.packet.Packet;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;

public class PacketRemoveWaypoint extends Packet {
    private String waypointName;

    public PacketRemoveWaypoint(Waypoint waypoint) {
        this.waypointName = waypoint.getName();
    }

    public PacketRemoveWaypoint(String waypointName) {
        this.waypointName = waypointName;
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(1);
        //waypoint id
        this.writeString(this.waypointName);
    }
}
