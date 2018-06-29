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
            if (args == null
                    || args.length == 0) {
                if (cs.hasPermission("clearchat.clear")) {
                    chatClear(this.config.getConfig().getInt("SendThisManyLines"));
                    Bukkit.broadcastMessage(getFancyMsg(this.config.getConfig().getString("BroadcastChatClear").replace("%player%", cs.getName())));
                    return true;
                } else {
                    cs.sendMessage(getFancyMsg(this.config.getConfig().getString("NoPerms")));
                    return false;
                }
            } else if (args.length == 1) {
                String args0 = args[0];
                if (args0.equalsIgnoreCase("reload")) {
                    if (cs.hasPermission("clearchat.reload")) {
                        this.config.reloadConfig();
                        cs.sendMessage(getFancyMsg(this.config.getConfig().getString("ReloadedConfig")));
                    } else {
                        cs.sendMessage(getFancyMsg(this.config.getConfig().getString("NoPerms")));
                    }
                } else if (args0.equalsIgnoreCase("-s")
                        || args0.equalsIgnoreCase("-silent")) {
                    if (cs.hasPermission("clearchat.clear.silent")) {
                        chatClear(this.config.getConfig().getInt("SendThisManyLines"));
                        Bukkit.broadcastMessage(getFancyMsg(this.config.getConfig().getString("BroadcastSilent")));
                    } else {
                        cs.sendMessage(getFancyMsg(this.config.getConfig().getString("NoPerms")));
                    }
                } else if (args0.equalsIgnoreCase("help")) {
                    if (cs.hasPermission("clearchat.help")) {
                        List<String> helpList = this.config.getConfig().getStringList("Help");
                        for (String msg : helpList) {
                            cs.sendMessage(getFancyMsg(msg));
                        }
                    }
                }
            } else {
                cs.sendMessage(getFancyMsg("TrollMessage"));
            }
        }
        return false;
    }

    private String getFancyMsg(String msg) {
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
