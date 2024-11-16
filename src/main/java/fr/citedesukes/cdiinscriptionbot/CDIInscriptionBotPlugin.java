package fr.citedesukes.cdiinscriptionbot;

import fr.citedesukes.cdiinscriptionbot.objects.RequestManager;
import fr.citedesukes.cdiinscriptionbot.runnable.ExpirationRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import fr.citedesukes.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesukes.cdiinscriptionbot.utils.ConfigManager;

public class CDIInscriptionBotPlugin extends JavaPlugin {
    private DiscordBot bot;
    private ConfigManager configManager;
    private CDIInscriptionBotPlugin plugin;
    private RequestManager requestManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        requestManager = new RequestManager();
        plugin = this;
        getLogger().info("CDIInscriptionBotPlugin enabled");

        bot = new DiscordBot(getConfig().getString("token"), this);
        bot.start();

        ExpirationRunnable expirationRunnable = new ExpirationRunnable(this);
        expirationRunnable.runTaskTimer(this, 0, 0);
    }

    public DiscordBot bot() {
        return bot;
    }

    public ConfigManager config() {
        return configManager;
    }

    public CDIInscriptionBotPlugin plugin() {
        return plugin;
    }
    public RequestManager requestManager() {
        return requestManager;
    }
}
