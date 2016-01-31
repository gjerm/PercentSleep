package cc.crypticcraft.percentsleep;

import com.earth2me.essentials.IEssentials;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kitteh.vanish.VanishPlugin;

public class PercentSleepWorld {

    private World world;
    private int percentageNeeded;
    private int playersSleeping;
    private int playerAmount;
    private FileConfiguration config = Bukkit.getServer().getPluginManager().getPlugin("PercentSleep").getConfig();
    private String displayName;
    private IEssentials essentials = (IEssentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    private VanishPlugin vanish = (VanishPlugin) Bukkit.getServer().getPluginManager().getPlugin("VanishNoPacket");



    public PercentSleepWorld(World w) {
        this.world = w;
        this.playersSleeping = 0;
        this.displayName = config.getString("worlds." + this.world.getName() + ".display-name", this.world.getName());
        this.percentageNeeded = config.getInt("worlds." + this.world.getName() + ".needed-percentage", config.getInt("default-percentage", 50));
    }

    public boolean skipNightIfPossible(boolean bc) {
        if (this.isNight()) {
            this.playerAmount = this.world.getPlayers().size();

            if (essentials != null || vanish != null) {
                for (Player p : this.world.getPlayers()) {
                    if (essentials != null && essentials.getUser(p).isAfk()) {
                        playerAmount--;
                    } else if (vanish != null && vanish.getManager().isVanished(p)) {
                        playerAmount--;
                    }
                }
            }

            float percentSleeping = ((float) this.playersSleeping / (float) this.playerAmount) * 100.0f;

            if (bc)
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ((int) percentSleeping) + "% are sleeping in " + this.displayName + " (" + this.percentageNeeded + "% needed).");

            if ((int) percentSleeping >= percentageNeeded) {
                this.world.setTime(0);
                if (config.getBoolean("clear-storms")) this.world.setStorm(false);
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

    public World getWorld() {
        return this.world;
    }

    public boolean isNight() {
        return this.world.getTime() < 24000 && this.world.getTime() > 12530;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
