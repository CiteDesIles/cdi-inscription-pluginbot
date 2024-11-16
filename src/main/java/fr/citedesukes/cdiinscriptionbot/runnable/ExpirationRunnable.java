package fr.citedesukes.cdiinscriptionbot.runnable;

import fr.citedesukes.cdiinscriptionbot.CDIInscriptionBotPlugin;
import fr.citedesukes.cdiinscriptionbot.objects.RequestManager;
import org.bukkit.scheduler.BukkitRunnable;

public class ExpirationRunnable extends BukkitRunnable {

    private final CDIInscriptionBotPlugin plugin;

    public ExpirationRunnable(CDIInscriptionBotPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        RequestManager requestManager = plugin.requestManager();
    }
}
