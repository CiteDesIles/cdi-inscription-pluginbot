package fr.citedesukes.cdiinscriptionbot;

import org.bukkit.plugin.java.JavaPlugin;

import fr.citedesukes.cdiinscriptionbot.bot.DiscordBot;
import fr.citedesukes.cdiinscriptionbot.utils.ConfigManager;

public class CDIInscriptionBotPlugin extends JavaPlugin {
    private DiscordBot bot;
    private ConfigManager configManager;
    private CDIInscriptionBotPlugin plugin;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        plugin = this;
        getLogger().info("CDIInscriptionBotPlugin enabled");

        bot = new DiscordBot(getConfig().getString("discord.token"));
        bot.start();
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
}
