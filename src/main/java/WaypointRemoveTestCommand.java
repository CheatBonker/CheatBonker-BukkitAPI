import com.cheatbonker.bukkitapi.dimension.Dimension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WaypointRemoveTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CheatBonkerBukkitAPI.apiInstance.removeWaypoint((Player) sender, args[0], Dimension.OVERWORLD);
        return true;
    }
}
