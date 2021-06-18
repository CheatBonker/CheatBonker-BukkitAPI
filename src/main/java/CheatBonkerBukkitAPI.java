import com.cheatbonker.bukkitapi.CheatBonkerAPI;
import com.cheatbonker.bukkitapi.dimension.Dimension;
import com.cheatbonker.bukkitapi.event.CheatBonkerRegisterEvent;
import com.cheatbonker.bukkitapi.module.CheatBonkerModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

//this is a test plugin (and example if you really want), the actual api is inside the com.cheatbonker.bukkitapi package
public final class CheatBonkerBukkitAPI extends JavaPlugin implements Listener {
    public static CheatBonkerAPI apiInstance;

    @Override
    public void onEnable() {
        apiInstance = new CheatBonkerAPI(this);
        getServer().getPluginCommand("waypointtest").setExecutor(new WaypointTestCommand());
        getServer().getPluginCommand("waypointremovetest").setExecutor(new WaypointRemoveTestCommand());
        getServer().getPluginCommand("staffmodulestest").setExecutor(new StaffModulesTestCommand());
        getServer().getPluginCommand("disallowedmodulestest").setExecutor(new DisallowedModulesTestCommand());
        getServer().getPluginCommand("notificationtest").setExecutor(new NotificationTestCommand());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onCheatBonkerRegisterEvent(CheatBonkerRegisterEvent event) {
        System.out.println(event.getPlayer().getName() + " is using cheatbonkeR!!!");
        apiInstance.sendNotification(event.getPlayer(), "you joined lmao", "yes yes", 5000);
        apiInstance.addWaypoint(event.getPlayer(), "Spawn", Dimension.OVERWORLD, 0, 69, 0, Color.WHITE);
        apiInstance.setDisallowedModules(event.getPlayer(), CheatBonkerModule.DIRECTION_HUD);
        if (event.getPlayer().isOp()) {
            apiInstance.setStaffModulesStatus(event.getPlayer(), true);
        }
    }
}
