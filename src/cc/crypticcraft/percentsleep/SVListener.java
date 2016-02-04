package cc.crypticcraft.percentsleep;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.EventPriority;
import de.myzelyam.api.vanish.PlayerHideEvent;



public class SVListener implements Listener {

    private final PercentSleep plugin;

    public SVListener(PercentSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onVanishEvent(PlayerHideEvent event) {
        PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        if (world != null) {
            world.skipNightIfPossible(false);
        }
    }
}
