import com.cheatbonker.bukkitapi.CheatBonkerAPI;
import org.bukkit.plugin.java.JavaPlugin;

//this is a test plugin (and example if you really want), the actual api is inside the com.cheatbonker.bukkitapi package
public final class CheatBonkerBukkitAPI extends JavaPlugin {
    public static CheatBonkerAPI apiInstance;

    @Override
    public void onEnable() {
        apiInstance = new CheatBonkerAPI(this);
        getServer().getPluginCommand("waypointtest").setExecutor(new WaypointTestCommand());
        getServer().getPluginCommand("waypointremovetest").setExecutor(new WaypointRemoveTestCommand());
        getServer().getPluginCommand("staffmodulestest").setExecutor(new StaffModulesTestCommand());
        getServer().getPluginCommand("disallowedmodulestest").setExecutor(new DisallowedModulesTestCommand());
    }

    @Override
    public void onDisable() {

    }
}
