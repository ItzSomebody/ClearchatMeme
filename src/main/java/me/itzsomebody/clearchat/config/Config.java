/*
 * Copyright (C) 2018 ItzSomebody
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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
            this.plugin.getLogger().severe("Something went wrong when trying to load the configuration files.");
            t.printStackTrace();
        }
    }

    public void reloadConfig() {
        this.config = new YamlConfiguration();
        try {
            this.config.load(configFile);
        } catch (Throwable t) {
            this.plugin.getLogger().severe("Something went wrong when trying to reload the configuration files.");
            t.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
