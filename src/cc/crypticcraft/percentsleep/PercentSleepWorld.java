package cc.crypticcraft.percentsleep;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import de.myzelyam.api.vanish.VanishAPI;

public class PercentSleepWorld {

    private final World world;
    private final String displayName;
    private int playersSleeping;

    private final int percentageNeeded;
    private final boolean skipStorms;
    private final boolean ignoreVanished;
    private final boolean ignoreAfk;

    public PercentSleepWorld(World w) {
        this.world = w;
        final FileConfiguration config = PercentSleep.plugin.getConfig();

        // Init config variables
        this.displayName = config.getString("worlds." + this.world.getName() + ".display-name", this.world.getName());
        this.percentageNeeded = config.getInt("worlds." + this.world.getName() + ".needed-percentage", config.getInt("default-percentage", 50));
        this.skipStorms = config.getBoolean("worlds." + this.world.getName() + ".clear-storms", config.getBoolean("get-storms", false));
        this.ignoreVanished = config.getBoolean("ignore-vanished", true);
        this.ignoreAfk = config.getBoolean("ignore-afk", true);
    }

    public boolean skipNightIfPossible(boolean bc) {
        if (this.isNight()) {
            int playerAmount = this.world.getPlayers().size();
            PluginManager pm = Bukkit.getPluginManager();
            if (PercentSleep.anyPluginsHooked) {
                for (Player p : this.world.getPlayers()) {
                    if (ignoreAfk && pm.isPluginEnabled("Essentials") && PercentSleep.essentials.getUser(p).isAfk()) {
                        playerAmount--;
                    } else if (ignoreVanished && pm.isPluginEnabled("VanishNoPacket") && PercentSleep.vanish.getManager().isVanished(p)) {
                        playerAmount--;
                    } else if (ignoreVanished && (pm.isPluginEnabled("SuperVanish") || pm.isPluginEnabled("PremiumVanish")) && VanishAPI.isInvisible(p)) {
                        playerAmount--;
                    }
                }
            }

            final int percentSleeping = (int) (((float) this.playersSleeping / (float) playerAmount) * 100.0f);

            if (bc)
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + (percentSleeping) + "% are sleeping in " + this.displayName + " (" + this.percentageNeeded + "% needed).");

            if (percentSleeping >= percentageNeeded) {
                this.world.setTime(0);
                if (this.skipStorms) this.world.setStorm(false);
                this.playersSleeping = 0;
                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Skipping the night in " + this.displayName + ". Rise and shine!");
                return true;
            }
        }
        return false;
    }


    public void setPlayersSleeping(int playersSleeping) {
        this.playersSleeping = playersSleeping;
    }

    public int getPlayersSleeping() {
        return playersSleeping;
    }


    public boolean isNight() {
        return this.world.getTime() < 24000 && this.world.getTime() > 12530;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
