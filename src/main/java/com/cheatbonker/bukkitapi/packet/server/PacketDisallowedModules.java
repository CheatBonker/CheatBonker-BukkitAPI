package com.cheatbonker.bukkitapi.packet.server;

import com.cheatbonker.bukkitapi.module.CheatBonkerModule;
import com.cheatbonker.bukkitapi.packet.Packet;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;

public class PacketDisallowedModules extends Packet {
    private final int[] disallowedModulesIds;

    public PacketDisallowedModules(CheatBonkerModule... disallowedModules) {
        List<Integer> moduleIds = new ArrayList<>();
        for (CheatBonkerModule cheatBonkerModule : disallowedModules) {
            moduleIds.add(cheatBonkerModule.getId());
        }
        this.disallowedModulesIds = Ints.toArray(moduleIds);
    }

    public PacketDisallowedModules(List<CheatBonkerModule> disallowedModules) {
        List<Integer> moduleIds = new ArrayList<>();
        for (CheatBonkerModule cheatBonkerModule : disallowedModules) {
            moduleIds.add(cheatBonkerModule.getId());
        }
        this.disallowedModulesIds = Ints.toArray(moduleIds);
    }

    @Override
    public void write() {
        //packet id
        this.buf.writeInt(2);
        //int array of disallowed modules
        this.writeIntArray(this.disallowedModulesIds);
    }
}
