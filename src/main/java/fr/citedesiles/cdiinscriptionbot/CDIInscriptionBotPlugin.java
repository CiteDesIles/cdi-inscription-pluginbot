package fr.citedesiles.cdiinscriptionbot;

import fr.citedesiles.cdiinscriptionbot.commands.LinksCommandExecutor;
import fr.citedesiles.cdiinscriptionbot.listener.*;
import fr.citedesiles.cdiinscriptionbot.objects.InviteManager;
import fr.citedesiles.cdiinscriptionbot.objects.RequestManager;
import fr.citedesiles.cdiinscriptionbot.mysql.DatabaseManager;
import fr.citedesiles.cdiinscriptionbot.runnable.ExpirationRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import fr.citedesiles.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesiles.cdiinscriptionbot.utils.ConfigManager;

import java.io.IOException;

public class CDIInscriptionBotPlugin extends JavaPlugin {
    private DiscordBot bot;
    private ConfigManager configManager;
    private static CDIInscriptionBotPlugin plugin;
    private RequestManager requestManager;
    private InviteManager inviteManager;
    public static Location spawn;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        requestManager = new RequestManager();
        inviteManager = new InviteManager();
        plugin = this;

        spawn = new Location(Bukkit.getWorld("world"), 329.5, 120, -320.5);

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

        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        getServer().getPluginManager().registerEvents(new OnBreak(), this);
        getServer().getPluginManager().registerEvents(new OnDamage(), this);
        getServer().getPluginManager().registerEvents(new OnMove(), this);

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
    public InviteManager inviteManager() {
        return inviteManager;
    }
}
