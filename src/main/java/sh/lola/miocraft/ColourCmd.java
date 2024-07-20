package sh.lola.miocraft;

import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ColourCmd implements CommandExecutor {

    // hex colour validation
    private static final Pattern HEXPATTERN = Pattern.compile("^#([a-fA-F0-9]{6})$");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData data = PlayerData.get(player);

        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.of(data.color) + "current colour: " + data.color);
            return true;
        }

        if (args.length != 1 || !HEXPATTERN.matcher(args[0]).matches()) {
            sender.sendMessage(ChatColor.GRAY + "usage: /colour #<6 digit rgb hex>");
            return true;
        }

        data.color = args[0].toLowerCase();
        data.saveData();

        sender.sendMessage(ChatColor.of(data.color) + "colour has been changed to " + data.color);
        player.setPlayerListName(ChatColor.of(data.color) + player.getName());

        return true;
    }

}
