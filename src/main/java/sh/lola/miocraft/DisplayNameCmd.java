package sh.lola.miocraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class DisplayNameCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData data = PlayerData.get(player);

        if (args.length == 0) {
            if (data.displayName.isEmpty()) {
                sender.sendMessage(ChatColor.GRAY + "no display name set");
            } else {
                sender.sendMessage(ChatColor.GRAY + "current display name: " + data.displayName);
            }

            return true;
        }

        String name = args[0];

        for (int i = 1; i < args.length; i++) {
            name += " " + args[i];
        }

        if (name.equalsIgnoreCase(player.getName())) {
            sender.sendMessage(ChatColor.GRAY + "display name removed");
        } else if (name.length() > 48) {
            sender.sendMessage(ChatColor.GRAY + "display name cannot be longer than 48 characters");
        } else {
            data.displayName = name;
            data.saveData();
            
            sender.sendMessage(ChatColor.GRAY + "display name set to \"" + data.displayName + "\"");
        }

        return true;
    }

}
