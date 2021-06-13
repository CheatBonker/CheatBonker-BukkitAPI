package com.cheatbonker.bukkitapi.packet;

import com.cheatbonker.bukkitapi.CBNetHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Packet {
    //packets in the server package are packets that are being sent from the server
    public static final Map<Integer, Class<? extends Packet>> incomingPacketMap = new HashMap<>();

    static {

    }

    public ByteBuf buf = Unpooled.buffer();

    //read the ByteBuf instance and write the thingys to vars
    public void read(ByteBuf message) {}
    //write data given to the packet via class constructor to `buf`
    public void write() {}
    //handle the packet
    public void handle(CBNetHandler cbNetHandler) {}

    //utility thingy
    public void writeString(String s) {
        this.buf.writeInt(s.getBytes().length);
        this.buf.writeBytes(s.getBytes(Charsets.UTF_8));
    }

    public String readString() {
        int length = this.buf.readInt();
        return this.buf.readBytes(length).toString(StandardCharsets.UTF_8);
    }

    public void writeIntArray(int[] arr) {
        byte[] byteArr = this.intArrToBytes(arr);
        this.buf.writeInt(byteArr.length);
        this.buf.writeBytes(byteArr);
    }

    public void writeColor(Color color) {
        this.buf.writeInt(color.getRed());
        this.buf.writeInt(color.getGreen());
        this.buf.writeInt(color.getBlue());
    }

    public UUID readUUID() {
        return new UUID(this.buf.readLong(), this.buf.readLong());
    }

    //thanks stack overflow
    private byte[] intArrToBytes(int[] data) {
        byte[] bytes = new byte[data.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) data[i];
        }
        return bytes;
    }
}
