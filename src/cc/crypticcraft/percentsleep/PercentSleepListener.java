package cc.crypticcraft.percentsleep;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.kitteh.vanish.VanishPlugin;

public class PercentSleepListener implements Listener {
    private final PercentSleep plugin;
    private final VanishPlugin vanish = (VanishPlugin) (VanishPlugin) Bukkit.getServer().getPluginManager().getPlugin("VanishNoPacket");

    public PercentSleepListener(PercentSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        final PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        final boolean playerVanished = PercentSleep.vanish.getManager().isVanished(event.getPlayer());
        final boolean playerAfk = PercentSleep.essentials.getUser(event.getPlayer()).isAfk();

        if (world != null && !playerVanished && !playerAfk) {
            Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GOLD + " has gone to sleep in " + world.getDisplayName() + ".");
            world.setPlayersSleeping(world.getPlayersSleeping() + 1);
            world.skipNightIfPossible(true);
        }
    }

    @EventHandler
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        final PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        final boolean playerVanished = PercentSleep.vanish.getManager().isVanished(event.getPlayer());
        final boolean playerAfk = PercentSleep.essentials.getUser(event.getPlayer()).isAfk();

        if (world != null) {
            boolean skipped = world.skipNightIfPossible(false);
            if (world.isNight() && !skipped && !playerVanished && !playerAfk) {
                Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GOLD + " is no longer sleeping.");
                world.setPlayersSleeping(world.getPlayersSleeping() - 1);
            }
            // This runs if the world somehow naturally skipped the night
            else if (!world.isNight() && !skipped) {
                world.setPlayersSleeping(0);
            }
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        final PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        if (world != null) {
            world.skipNightIfPossible(false);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        final PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        if (world != null) {
            if (event.getPlayer().isSleeping()) world.setPlayersSleeping(world.getPlayersSleeping() - 1);
            world.skipNightIfPossible(false);
        }
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        final PercentSleepWorld worldTo = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        final PercentSleepWorld worldFrom = plugin.getWorlds().get(event.getFrom().getName());

        if (worldTo != null) {
            worldTo.skipNightIfPossible(false);
        }
        if (worldFrom != null) {
            worldFrom.skipNightIfPossible(false);
        }
    }
}
