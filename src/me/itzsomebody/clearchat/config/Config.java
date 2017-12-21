package me.itzsomebody.clearchat.config;

import me.itzsomebody.clearchat.ClearChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    private ClearChat plugin;
    private File configFile;
    private FileConfiguration config;

    public Config(ClearChat plugin) {
        this.plugin = plugin;
    }

    public void createConfigs() {
        configFile = new File(this.plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            this.plugin.saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (Throwable t) {
            this.plugin.getLogger().severe("Something went wrong when trying to create the configuration files.");
            t.printStackTrace();
        }
    }

    public void reloadConfig() {
        this.config = new YamlConfiguration();
        try {
            this.config.load(configFile);
        } catch (Throwable t) {
            this.plugin.getLogger().severe("Something went wrong when trying to create the configuration files.");
            t.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
