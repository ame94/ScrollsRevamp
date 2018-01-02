/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.command;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import scrolls.ScrollsPlugin;
import scrolls.configuration.DataTrackType;
import scrolls.event.scroll.ScrollSpawnedEvent;
import scrolls.inventory.ScrollMath;
import scrolls.inventory.ScrollType;

/**
 *
 * @author knaxel
 */
public class ScrollsCommand implements CommandExecutor {

    private final ScrollsPlugin plugin;
    private final ScrollMath scrollMath;

    public ScrollsCommand(ScrollsPlugin plugin) {
        this.plugin = plugin;
        this.scrollMath = plugin.getScrollMath();
    }

    private void correctUse(CommandSender player, String rest) {
        player.sendMessage(ChatColor.WHITE + "Correct usage for that command is... \n" + ChatColor.RED + "'/scrolls " + rest + "'");
    }

    private void permissionCheck(CommandSender cs) {
        cs.sendMessage(ChatColor.RED + "You don't have permission for that command!");
    }

    private void sendHelp(CommandSender player) {
        String message = "--------------------------------------------------\n"
                + ChatColor.GOLD + "                 SCROLLS HELPS 1\n" + ChatColor.WHITE;
        int count = 0;
        if (player.hasPermission("scrolls.command.get")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls get <type>" + ChatColor.WHITE + " - will get you a random type of scroll.\n");
        }
        if (player.hasPermission("scrolls.command.get.more")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls get <type> <amount>" + ChatColor.WHITE + " - will get you many scrolls of the same type\n");
        }
        if (player.hasPermission("scrolls.command.help")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls help" + ChatColor.WHITE + " - will show you this message.\n");
        }
        if (player.hasPermission("scrolls.command.reload")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls reload" + ChatColor.WHITE + " - will reload the plugins config file.\n");
        }
        if (player.hasPermission("scrolls.command.data.toggle")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls data" + ChatColor.WHITE + " - will toggle whether scrolls creation data should be recorded.\n");
        }
        if (player.hasPermission("scrolls.command.data.save")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls data save" + ChatColor.WHITE + " - will save what data has been recorded if any.\n");
        }
        player.sendMessage(message + "--------------------------------------------------\n");
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] arg) {
        Player player;
        if (arg.length == 0) {
            sendHelp(cs);
            return true;
        }
        switch (arg[0].toLowerCase()) {
            default: {
                sendHelp(cs);
                return true;
            }
            case "help": {
                sendHelp(cs);
                return true;
            }
            case "data": {
                if(!cs.hasPermission("scrolls.command.data")){
                    permissionCheck(cs);
                    return true;
                }
                if (arg.length > 1) {
                    if (!arg[1].equalsIgnoreCase("save")) {
                        
                        String message = "Scroll data command usage : \n";
                        if (cs.hasPermission("scrolls.command.data.toggle")) {
                            message += (1 + "" + ChatColor.GRAY + ". /scrolls data" + ChatColor.WHITE + " - will toggle whether scrolls creation data should be recorded.\n");
                        }
                        if (cs.hasPermission("scrolls.command.data.save")) {
                            message += (2 + "" + ChatColor.GRAY + ". /scrolls data save" + ChatColor.WHITE + " - will save what data has been recorded if any.\n");
                        }
                        cs.sendMessage(message);
                        return true;
                        
                    }else{
                        try{
                            
                            plugin.saveDataFile();
                        }catch (Exception e) {
                            e.printStackTrace();
                            cs.sendMessage("There was an error creating the data file!");
                            return true;
                        }
                        cs.sendMessage("Successfully saved the data file, check for the most recently edited in the data folder. Tracking is presumabley no longer needed.");
                    }
                }
                plugin.toggleTracking();
                String message = "Scroll tracking is now %8s";
                if(plugin.isTrackingScrollData())
                    cs.sendMessage(String.format(message, "enabled"));
                else
                    cs.sendMessage(String.format(message, "disabled"));
                return true;
            }
            case "get": {
                if (!cs.hasPermission("scrolls.command.get")) {
                    permissionCheck(cs);
                    return true;
                }
                if(!(cs instanceof Player)){
                    cs.sendMessage("That command is for players to use only! Sorry for the inconvinience.");
                    return true;
                }
                player = (Player) cs;
                if (arg.length < 2) {
                    if (cs.hasPermission("scrolls.command.get.more")) {
                        correctUse(player, "get <type> <amount>");
                        player.sendMessage(ChatColor.GRAY + "Types of scrolls include: darkscroll , basicscroll,chaosscroll, and cleanslatescroll");
                        return true;
                    }
                    correctUse(player, "get <type>");
                    player.sendMessage(ChatColor.GRAY + "Types of scrolls include: darkscroll , basicscroll,chaosscroll, and cleanslatescroll");
                    return true;
                }
                switch (arg[1].toLowerCase()) {
                    case "cleanslatescroll": {
                        int i = 1;
                        if (player.hasPermission("scrolls.command.get.more")) {
                            try {
                                i = Integer.parseInt(arg[2]);
                            } catch (NumberFormatException e) {
                                correctUse(player, "get <type> <amount>");
                                return true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.CLEANSLATE);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.CLEANSLATE);
                            player.getInventory().addItem(s);
                            player.updateInventory();
                            ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                            plugin.getPluginManager().callEvent(spawnedEvent);
                            i--;
                        }
                        return true;
                    }
                    case "darkscroll": {
                        int i = 1;
                        if (player.hasPermission("scrolls.command.get.more")) {
                            try {
                                i = Integer.parseInt(arg[2]);
                            } catch (NumberFormatException e) {
                                correctUse(player, "get <type> <amount>");
                                return true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.DARK);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.DARK);
                            player.getInventory().addItem(s);
                            player.updateInventory();
                            ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                            plugin.getPluginManager().callEvent(spawnedEvent);
                            i--;
                        }
                        return true;
                    }
                    case "basicscroll": {
                        int i = 1;
                        if (player.hasPermission("scrolls.command.get.more")) {
                            try {
                                i = Integer.parseInt(arg[2]);
                            } catch (NumberFormatException e) {
                                correctUse(player, "get <type> <amount>");
                                return true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.BASIC);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.BASIC);
                            player.getInventory().addItem(s);
                            player.updateInventory();
                            ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                            plugin.getPluginManager().callEvent(spawnedEvent);
                            i--;
                        }
                        return true;
                    }
                    case "chaosscroll": {
                        int i = 1;
                        if (player.hasPermission("scrolls.command.get.more")) {
                            try {
                                i = Integer.parseInt(arg[2]);
                            } catch (NumberFormatException e) {
                                correctUse(player, "get <type> <amount>");
                                return true;
                            } catch (ArrayIndexOutOfBoundsException e) {
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.CHAOTIC);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.CHAOTIC);
                            player.getInventory().addItem(s);
                            player.updateInventory();
                            ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                            plugin.getPluginManager().callEvent(spawnedEvent);
                            i--;
                        }
                        return true;
                    }
                    default: {
                        if (player.hasPermission("scrolls.command.get.more")) {
                            correctUse(player, "get <type> <amount>");
                            player.sendMessage(ChatColor.GRAY + "Types of scrolls include: darkscroll , basicscroll,chaosscroll, and cleanslatescroll");
                            return true;
                        }
                        correctUse(player, "get <type>");
                        player.sendMessage(ChatColor.GRAY + "Types of scrolls include: darkscroll , basicscroll,chaosscroll, and cleanslatescroll");
                        return true;
                    }
                }
            }
            case "reload": {
                if (!cs.hasPermission("scrolls.command.reload")) {
                    permissionCheck(cs);
                    return true;
                }
                plugin.reload();
                cs.sendMessage("Success!");
                return true;
            }
        }
    }

}
