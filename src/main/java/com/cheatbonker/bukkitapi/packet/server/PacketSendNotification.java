package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.packet.Packet;

public class PacketSendNotification extends Packet {
    private String title, description;
    private long length;

    public PacketSendNotification(String title, String description, long length) {
        this.title = title;
        this.description = description;
        this.length = length;
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(4);
        //notification details
        this.writeString(this.title);
        this.writeString(this.description);
        this.buf.writeLong(this.length);
    }
}
