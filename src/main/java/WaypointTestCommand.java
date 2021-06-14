import com.cheatbonker.bukkitapi.dimension.Dimension;
import com.cheatbonker.bukkitapi.waypoints.Waypoint;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class WaypointTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CheatBonkerBukkitAPI.apiInstance.addWaypoint((Player) sender, new Waypoint(args[0], Dimension.OVERWORLD, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), new Color(Integer.parseInt(args[4]))));

        return true;
    }
}
