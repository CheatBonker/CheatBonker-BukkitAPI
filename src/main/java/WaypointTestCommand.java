import com.cheatbonker.bukkitapi.waypoints.Waypoint;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class WaypointTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CheatBonkerBukkitAPI.apiInstance.addWaypoint((Player) sender, new Waypoint("DFJIFDHJGOfd idek what tyoo type here yes", 420, 69, 420, new Color(32, 65, 99)));

        return true;
    }
}
