package fr.citedesukes.cdiinscriptionbot;

import fr.citedesukes.cdiinscriptionbot.objects.RequestManager;
import fr.citedesukes.cdiinscriptionbot.postgresl.DatabaseManager;
import fr.citedesukes.cdiinscriptionbot.runnable.ExpirationRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import fr.citedesukes.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesukes.cdiinscriptionbot.utils.ConfigManager;

public class CDIInscriptionBotPlugin extends JavaPlugin {
    private DiscordBot bot;
    private ConfigManager configManager;
    private static CDIInscriptionBotPlugin plugin;
    private RequestManager requestManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        requestManager = new RequestManager();
        plugin = this;

        DatabaseManager.initAllDataBaseConnections();

        getLogger().info("CDIInscriptionBotPlugin enabled");

        bot = new DiscordBot(getConfig().getString("token"), this);
        bot.start();

        ExpirationRunnable expirationRunnable = new ExpirationRunnable(this);
        expirationRunnable.runTaskTimer(this, 0, 0);

        plugin = this;
    }

    public DiscordBot bot() {
        return bot;
    }
    public ConfigManager config() {
        return configManager;
    }
    public static CDIInscriptionBotPlugin instance() {
        return plugin;
    }
    public RequestManager requestManager() {
        return requestManager;
    }
}
