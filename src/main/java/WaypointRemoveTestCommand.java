import com.cheatbonker.bukkitapi.dimension.Dimension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WaypointRemoveTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CheatBonkerBukkitAPI.apiInstance.removeWaypoint((Player) sender, "Overworld waypoint", Dimension.OVERWORLD);
        CheatBonkerBukkitAPI.apiInstance.removeWaypoint((Player) sender, "Nether waypoint", Dimension.NETHER);
        CheatBonkerBukkitAPI.apiInstance.removeWaypoint((Player) sender, "The End waypoint", Dimension.THE_END);
        return true;
    }
}
