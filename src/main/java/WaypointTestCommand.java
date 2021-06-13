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
        CheatBonkerBukkitAPI.apiInstance.addWaypoint((Player) sender, new Waypoint("Overworld waypoint", Dimension.OVERWORLD, 420, 69, 420, new Color(0, 169, 169)));
        CheatBonkerBukkitAPI.apiInstance.addWaypoint((Player) sender, new Waypoint("Nether waypoint", Dimension.NETHER, 420, 69, 420, new Color(0, 169, 169)));
        CheatBonkerBukkitAPI.apiInstance.addWaypoint((Player) sender, new Waypoint("The End waypoint", Dimension.THE_END, 420, 69, 420, new Color(0, 169, 169)));

        return true;
    }
}
