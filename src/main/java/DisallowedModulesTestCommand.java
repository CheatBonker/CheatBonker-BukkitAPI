import com.cheatbonker.bukkitapi.module.CheatBonkerModule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisallowedModulesTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CheatBonkerBukkitAPI.apiInstance.setDisallowedModules((Player) sender, CheatBonkerModule.DIRECTION_HUD, CheatBonkerModule.REACH_DISPLAY, CheatBonkerModule.WAYPOINTS);
        return true;
    }
}
