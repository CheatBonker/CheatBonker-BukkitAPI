import com.cheatbonker.bukkitapi.CheatBonkerAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class CheatBonkerBukkitAPI extends JavaPlugin {
    public static CheatBonkerAPI apiInstance;

    @Override
    public void onEnable() {
        apiInstance = new CheatBonkerAPI(this);
        getServer().getPluginCommand("waypointtest").setExecutor(new WaypointTestCommand());
    }

    @Override
    public void onDisable() {

    }
}
