package com.cheatbonker.bukkitapi.module;

public enum CheatBonkerModule {
    //only listed the mods that you would possible want to disable like why tf would you want to disable fps mod thats why i not liST IT OPOPSO PO SPO PO
    DIRECTION_HUD(0),
    REACH_DISPLAY(1),
    FULLBRIGHT(2),
    WAYPOINTS(3);

    private final int id;

    CheatBonkerModule(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
