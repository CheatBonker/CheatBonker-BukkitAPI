import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffModulesTestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        CheatBonkerBukkitAPI.apiInstance.setStaffModulesStatus(p, Boolean.parseBoolean(args[0]));
        return true;
    }
}
