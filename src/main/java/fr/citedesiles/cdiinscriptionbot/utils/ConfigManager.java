package fr.citedesiles.cdiinscriptionbot.utils;

import fr.citedesiles.cdiinscriptionbot.CDIInscriptionBotPlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ConfigManager {

    private final CDIInscriptionBotPlugin plugin;
    private Configuration configCache;

    public ConfigManager(CDIInscriptionBotPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean checkIfConfigExist() {
        File folder = plugin.getDataFolder();
        File configFile = new File(folder, "config.yml");
        return configFile.exists();
    }

    public void createConfigFile() {
        File folder = plugin.getDataFolder();
        File configFile = new File(folder, "config.yml");

        if (!folder.exists()) {
            if(folder.mkdir()) {
                plugin.getLogger().info("Plugin folder has been created");
            } else {
                plugin.getLogger().severe("An error occurred while creating plugin folder");
            }
        }

        if (!configFile.exists()) {
            try (InputStream inputStream = plugin.getResource("config.yml");
                 OutputStream outputStream = new FileOutputStream(configFile)) {

                byte[] buffer = new byte[1024];
                int length;
                while (true) {
                    assert inputStream != null;
                    if (!((length = inputStream.read(buffer)) > 0)) break;
                    outputStream.write(buffer, 0, length);
                }
                plugin.getLogger().info("config.yml has been created");
            } catch (IOException e) {
                plugin.getLogger().severe("An error occurred while creating config.yml");
            }
        }
    }

    public void loadConfig() throws IOException {
        if (!checkIfConfigExist()) createConfigFile();
        updateConfig();
        plugin.getLogger().info("Config loaded");
    }

    public void updateConfig() throws IOException {
        Configuration compiledConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        configCache = compiledConfig;

        // P
    }

    public Object configValue(String key) throws IOException {
        if (configCache == null) {
            configCache = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        }
        return configCache.get(key);
    }

    public String getString(String key) {
        try {
            Object value = configValue(key);
            if (value == null) {
                plugin.getLogger().severe("Key " + key + " is null in config");
                return null;
            }

            if (!(value instanceof String)) {
                plugin.getLogger().severe("Key " + key + " is not a string in config");
                return null;
            }

            return (String) value;
        } catch (IOException e) {
            plugin.getLogger().severe("An error occurred while getting key " + key + " in config");
            return null;
        }
    }

    public int getInt(String key) {
        try {
            Object value = configValue(key);
            if (value == null) {
                plugin.getLogger().severe("Key " + key + " not found in config");
                return -1;
            }

            if (!(value instanceof Integer)) {
                plugin.getLogger().severe("Key " + key + " is not an integer in config");
                return -1;
            }

            return (int) value;
        } catch (IOException e) {
            plugin.getLogger().severe("An error occurred while getting key " + key + " in config");
            return -1;
        }
    }
}
