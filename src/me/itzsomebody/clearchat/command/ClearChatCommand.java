package me.itzsomebody.clearchat.command;

import me.itzsomebody.clearchat.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ClearChatCommand implements CommandExecutor {
    private Config config;

    public ClearChatCommand(Config config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearchat")) {
            if (cs.hasPermission("clearchat.clear")) {
                if (args == null
                        || args.length == 0) {
                    chatClear(this.config.getConfig().getInt("SendThisManyLines"));
                    Bukkit.broadcastMessage(printPrefixedMsg(this.config.getConfig().getString("BroadcastChatClear").replace("%player%", cs.getName())));
                    return true;
                } else if (args.length == 1) {
                    String args0 = args[0];
                    if (args0.equalsIgnoreCase("reload")) {
                        if (cs.hasPermission("clearchat.reload")) {
                            this.config.reloadConfig();
                            cs.sendMessage(printPrefixedMsg(this.config.getConfig().getString("ReloadedConfig")));
                        } else {
                            cs.sendMessage(printPrefixedMsg(this.config.getConfig().getString("NoPerms")));
                        }
                    } else if (args0.equalsIgnoreCase("-s")
                            || args0.equalsIgnoreCase("-silent")) {
                        if (cs.hasPermission("clearchat.clear.silent")) {
                            chatClear(this.config.getConfig().getInt("SendThisManyLines"));
                            Bukkit.broadcastMessage(printPrefixedMsg(this.config.getConfig().getString("BroadcastSilent")));
                        } else {
                            cs.sendMessage(printPrefixedMsg(this.config.getConfig().getString("NoPerms")));
                        }
                    } else if (args0.equalsIgnoreCase("help")) {
                        if (cs.hasPermission("clearchat.help")) {
                            List<String> helpList = this.config.getConfig().getStringList("Help");
                            for (String msg : helpList) {
                                cs.sendMessage(printPrefixedMsg(msg));
                            }
                        }
                    }
                } else {
                    cs.sendMessage(printPrefixedMsg("TrollMessage"));
                }
            } else {
                cs.sendMessage(printPrefixedMsg(this.config.getConfig().getString("NoPerms")));
            }
        }
        return false;
    }

    private String printPrefixedMsg(String msg) {
        return ChatColor.translateAlternateColorCodes('&', this.config.getConfig().getString("Prefix") + msg);
    }

    private void chatClear(int times) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < times; i++) {
                player.sendMessage("");
            }
        }
    }
}
