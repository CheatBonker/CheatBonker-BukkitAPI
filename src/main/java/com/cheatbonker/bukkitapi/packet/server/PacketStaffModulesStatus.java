package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.packet.Packet;

public class PacketStaffModulesStatus extends Packet {
    private boolean status;

    public PacketStaffModulesStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(3);
        //status
        this.buf.writeBoolean(status);
    }
}
