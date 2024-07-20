package sh.lola.miocraft;

import org.bukkit.plugin.java.JavaPlugin;

public class TheGays extends JavaPlugin {

    // the ultimate global homo.
    // (active instance of plugin, fuck you OOP and java i hate you)
    static TheGays globalHomo;

    PingHUD ping;

    @Override
    public void onEnable() {
        globalHomo = this;

        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);

        getCommand("colour").setExecutor(new ColourCmd());
        getCommand("displayname").setExecutor(new DisplayNameCmd());

        ping = new PingHUD();
    }

    @Override
    public void onDisable() {
        ping = null;
        globalHomo = null;
    }

}
