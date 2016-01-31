package cc.crypticcraft.percentsleep;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.vanish.event.VanishStatusChangeEvent;



public class PercentSleepVanishListener implements Listener {

    private final PercentSleep plugin;

    public PercentSleepVanishListener(PercentSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVanishEvent(VanishStatusChangeEvent event) {
        PercentSleepWorld world = plugin.getWorlds().get(event.getPlayer().getWorld().getName());
        if (world != null) {
            world.skipNightIfPossible(false);
        }
    }
}
