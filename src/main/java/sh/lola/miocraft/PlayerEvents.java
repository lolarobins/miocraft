package sh.lola.miocraft;

import java.time.Instant;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class PlayerEvents implements Listener {

    @EventHandler
    public void joinEvent(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        Player p = e.getPlayer();
        PlayerData data = PlayerData.get(p);

        TextComponent text = new TextComponent(
                (data.displayName.isEmpty() ? p.getName() : data.displayName) + " joined the server.");
        text.setColor(ChatColor.of(data.color));

        if (!data.displayName.isEmpty()) {
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new Text(TextComponent.fromLegacyText(ChatColor.GRAY + p.getName()))));
        }

        Bukkit.getServer().spigot().broadcast(text);

        p.setPlayerListName(ChatColor.of(data.color) + p.getName());
    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent e) {
        e.setQuitMessage(null);

        Player p = e.getPlayer();
        PlayerData data = PlayerData.get(p);

        TextComponent text = new TextComponent(
                (data.displayName.isEmpty() ? p.getName() : data.displayName) + " left the server.");
        text.setColor(ChatColor.of(data.color));

        if (!data.displayName.isEmpty()) {
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new Text(TextComponent.fromLegacyText(ChatColor.GRAY + p.getName()))));
        }

        Bukkit.getServer().spigot().broadcast(text);

        data.lastOnline = Instant.now().getEpochSecond();
        data.saveData();

        PlayerData.cache.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e) {
        // cancel default msg
        e.setCancelled(true);

        Player p = e.getPlayer();
        PlayerData data = PlayerData.get(p);

        Bukkit.getLogger().log(Level.INFO, p.getName() + ": " + e.getMessage());

        TextComponent name = new TextComponent((data.displayName.isEmpty() ? p.getName() : data.displayName));
        name.setColor(ChatColor.of(data.color));
        name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + p.getName() + " "));

        if (!data.displayName.isEmpty()) {
            name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new Text(TextComponent.fromLegacyText(ChatColor.GRAY + p.getName()))));
        }

        Bukkit.getServer().spigot()
                .broadcast(new ComponentBuilder(name)
                        .append(TextComponent.fromLegacyText(ChatColor.GRAY + ": " + ChatColor.RESET + e.getMessage()))
                        .create());
    }

}
