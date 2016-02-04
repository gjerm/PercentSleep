package cc.crypticcraft.percentsleep;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PercentSleepListener implements Listener {
    private final PercentSleep plugin;

    public PercentSleepListener(PercentSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
        PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());

        if (world != null) {
            Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GOLD + " has gone to sleep in " + world.getDisplayName() + ".");
            world.setPlayersSleeping(world.getPlayersSleeping() + 1);
            world.skipNightIfPossible(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());

        if (world != null) {
            boolean skipped = world.skipNightIfPossible(false);
            if (world.isNight() && !skipped) {
                Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.GOLD + " is no longer sleeping.");
                world.setPlayersSleeping(world.getPlayersSleeping() - 1);
            }
            // This runs if the world somehow naturally skipped the night
            else if (!world.isNight() && !skipped) {
                world.setPlayersSleeping(0);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        if (world != null) {
            world.skipNightIfPossible(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        if (world != null) {
            if (event.getPlayer().isSleeping()) world.setPlayersSleeping(world.getPlayersSleeping() - 1);
            world.skipNightIfPossible(false);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        PercentSleepWorld worldTo = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        PercentSleepWorld worldFrom = plugin.getWorlds().get(event.getFrom().getName());

        if (worldTo != null) {
            worldTo.skipNightIfPossible(false);
        }
        if (worldFrom != null) {
            worldFrom.skipNightIfPossible(false);
        }
    }
}
