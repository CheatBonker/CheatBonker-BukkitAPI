package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.packet.Packet;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;

public class PacketRemoveWaypoint extends Packet {
    private String waypointName;
    private String dimensionName;

    public PacketRemoveWaypoint(Waypoint waypoint) {
        this.waypointName = waypoint.getName();
        this.dimensionName = waypoint.getDimensionName();
    }

    public PacketRemoveWaypoint(String waypointName, String dimensionName) {
        this.waypointName = waypointName;
        this.dimensionName = dimensionName;
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(1);
        //waypoint name
        this.writeString(this.waypointName);
        //waypoint dimension
        this.writeString(this.dimensionName);
    }
}
