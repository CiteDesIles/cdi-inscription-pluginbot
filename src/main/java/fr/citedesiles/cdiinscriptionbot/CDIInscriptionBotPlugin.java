package fr.citedesiles.cdiinscriptionbot;

import fr.citedesiles.cdiinscriptionbot.commands.LinksCommandExecutor;
import fr.citedesiles.cdiinscriptionbot.objects.RequestManager;
import fr.citedesiles.cdiinscriptionbot.postgresl.DatabaseManager;
import fr.citedesiles.cdiinscriptionbot.runnable.ExpirationRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import fr.citedesiles.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesiles.cdiinscriptionbot.utils.ConfigManager;

import java.io.IOException;

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

        try {
            configManager.loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getCommand("link").setExecutor(new LinksCommandExecutor(this));

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
