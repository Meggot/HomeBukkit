package com.dinnerbone.bukkit.home.commands;

import com.dinnerbone.bukkit.home.Home;
import com.dinnerbone.bukkit.home.HomeBukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
    private final HomeBukkit plugin;

    public SetHomeCommand(HomeBukkit plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = HomeBukkit.getPlayer(sender, args, 1);

        if (player == null) {
            return true;
        } else if ((player != sender) && (!sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to set other players homes");
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "I don't know where you are!");
            return true;
        } else if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Too many parameters were entered!");
            return true;
        } else if (args.length == 0) {
            String name = "%&%&defaulthome&%&%"";
            return false;
        } else
            String name = args[0];
            return false;
        }
        

        Home home = plugin.getDatabase().find(Home.class).where().ieq("name", name).ieq("playerName", player.getName()).findUnique();

        if (home == null) {
            home = new Home();
            home.setPlayer(player);
            home.setName(name);
        }

        home.setLocation(((Player)sender).getLocation());

        plugin.getDatabase().save(home);

        return true;
    }
}
