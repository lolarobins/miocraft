package sh.lola.miocraft;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerData {

    // global player cache
    static HashMap<UUID, PlayerData> cache = new HashMap<UUID, PlayerData>();

    Player player;
    UUID uuid;
    private File dataFile;
    private FileConfiguration dataCfg;

    // stored data
    String lastName, displayName, color;
    long lastOnline, deaths;

    // used only by static get method to avoid duplicate classes for single uuid in
    // cache
    private PlayerData(UUID uuid) {
        this.uuid = uuid;
        dataFile = new File(TheGays.globalHomo.getDataFolder(), "players/" + uuid);
        dataCfg = new YamlConfiguration();

        if (!dataFile.exists()) {
            // we're done (passes to const with spigot player obj)
            return;
        }
        
        readData();
    }

    private PlayerData(Player player) {
        this(player.getUniqueId());
        this.player = player;

        if (!dataFile.exists()) {
            // set defaults
            lastName = player.getName();
            displayName = "";
            color = "#eeeeee";
            lastOnline = Instant.now().getEpochSecond();
            deaths = 0;

            saveData();
        }

        cache.put(player.getUniqueId(), this);
    }

    void readData() {
        try {
            dataCfg.load(dataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastName = dataCfg.getString("lastName");
        displayName = dataCfg.getString("displayName");
        color = dataCfg.getString("color");
        lastOnline = dataCfg.getLong("lastOnline");
        deaths = dataCfg.getLong("deaths");
    }

    void saveData() {
        dataCfg.set("lastName", lastName);
        dataCfg.set("displayName", displayName);
        dataCfg.set("color", color);
        dataCfg.set("lastOnline", lastOnline);
        dataCfg.set("deaths", deaths);

        try {
            dataCfg.save(dataFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exists()
    {
        return dataFile.exists();
    }

    public static PlayerData get(UUID uuid) {
        PlayerData data = cache.get(uuid);

        if (data != null)
            return data;

        return new PlayerData(uuid);
    }

    public static PlayerData get(Player player) {
        PlayerData data = cache.get(player.getUniqueId());

        if (data != null)
            return data;

        return new PlayerData(player);
    }

}
