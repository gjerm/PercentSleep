package cc.crypticcraft.percentsleep;

import com.earth2me.essentials.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.VanishPlugin;

import java.util.HashMap;
import java.util.List;

public class PercentSleep extends JavaPlugin {

    private HashMap<String, PercentSleepWorld> worlds = new HashMap<String, PercentSleepWorld>();

    public static IEssentials essentials = (IEssentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    public static VanishPlugin vanish = (VanishPlugin) Bukkit.getServer().getPluginManager().getPlugin("VanishNoPacket");
    public static PercentSleep plugin;


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new PercentSleepListener(this), this);
        plugin = this;

        // Hook Vanish/Essentials listeners if the plugins exist
        if (essentials != null) {
            Bukkit.getServer().getPluginManager().registerEvents(new PercentSleepEssentialsListener(this), this);
            this.getLogger().info("Hooked into Essentials");
        }
        if (vanish != null) {
            Bukkit.getServer().getPluginManager().registerEvents(new PercentSleepVanishListener(this), this);
            this.getLogger().info("Hooked into VanishNoPacket");
        }

        final List<World> worlds = Bukkit.getWorlds();
        final List<String> ignored = this.getConfig().getStringList("ignored-worlds");

        for (World w: worlds) {
            if (w.getEnvironment().equals(World.Environment.NORMAL) && !ignored.contains(w.getName())) {
                this.worlds.put(w.getName(), new PercentSleepWorld(w));
            }
        }
        getLogger().info(this.worlds.toString());
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    public HashMap<String, PercentSleepWorld> getWorlds() {
        return this.worlds;
    }
}
