package me.itzsomebody.clearchat;

import me.itzsomebody.clearchat.command.ClearChatCommand;
import me.itzsomebody.clearchat.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearChat extends JavaPlugin {
    public void onEnable() {
        Config config = new Config(this);
        config.createConfigs();
        this.getCommand("clearchat").setExecutor(new ClearChatCommand(config));
        this.getLogger().info("ClearChat enabled!");
    }
}
