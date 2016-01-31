package cc.crypticcraft.percentsleep;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;



public class PercentSleepEssentialsListener implements Listener {

    private final PercentSleep plugin;

    public PercentSleepEssentialsListener(PercentSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAfkStatusChangeEvent(AfkStatusChangeEvent event) {
        Player p = event.getAffected().getBase();
        PercentSleepWorld world = plugin.getWorlds().get(p.getWorld().getName());
        if (world != null) {
            world.skipNightIfPossible(false);
        }
    }
}
