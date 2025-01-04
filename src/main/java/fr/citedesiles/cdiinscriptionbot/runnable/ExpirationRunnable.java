package fr.citedesiles.cdiinscriptionbot.runnable;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesiles.cdiinscriptionbot.objects.RequestManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpirationRunnable extends BukkitRunnable {

    private final CDIInscriptionBotPlugin plugin;

    public ExpirationRunnable(CDIInscriptionBotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.requestManager().removeAtickToAllRequest();
        plugin.inviteManager().tick();
    }
}
