package fr.citedesukes.cdiinscriptionbot.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.citedesukes.cdiinscriptionbot.CDIInscriptionBotPlugin;

import java.io.File;

public class ConfigManager {
    
    private final CDIInscriptionBotPlugin plugin;

    private YamlConfiguration config;

    public ConfigManager(CDIInscriptionBotPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.saveDefaultConfig();
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }

    public YamlConfiguration yamlconfig() {
        return config;
    }

    public void save() {
        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }
}
