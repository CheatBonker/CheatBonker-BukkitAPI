package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.packet.Packet;

public class PacketDiscordRPServerInfo extends Packet {
    private String serverInfo;

    public PacketDiscordRPServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(5);
        //server info string
        this.writeString(this.serverInfo);
    }
}
