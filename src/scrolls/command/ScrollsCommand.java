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

    public ScrollsCommand(ScrollsPlugin plugin, ScrollMath math) {
        this.plugin = plugin;
        this.scrollMath = math;
    }

    private void correctUse(Player player, String rest) {
        player.sendMessage(ChatColor.WHITE + "CorrectUsage \n" + ChatColor.RED + "'/scrolls " + rest + "'");
    }

    private void permissionCheck(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have permission for that command!");
    }

    private void permissionCheck(CommandSender cs) {
        cs.sendMessage(ChatColor.RED + "You don't have permission for that command!");
    }

    private void sendHelp(Player player) {
        String message = "--------------------------------------------------\n"
                + ChatColor.GOLD + "                 SCROLLS HELPS 1\n" + ChatColor.WHITE;
        int count = 0;
        if (player.hasPermission("scrolls.command.get")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls get <type>" + ChatColor.WHITE + " - will get you a random type of scroll.\n");
        }
        if (player.hasPermission("scrolls.command.get.more")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls get <type> <amount>" + ChatColor.WHITE + "- will get you many scrolls of the same type\n");
        }
        if (player.hasPermission("scrolls.command.help")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls help" + ChatColor.WHITE + "- will show you this message.\n");
        }
        if (player.hasPermission("scrolls.command.reload")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls reload" + ChatColor.WHITE + "- will reload the plugins config file.\n");
        }
        if (player.hasPermission("scrolls.command.data.toggle")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls data <true/false>" + ChatColor.WHITE + "- will reload the plugins config file.\n");
        }
        if (player.hasPermission("scrolls.command.data.view")) {
            count++;
            message += (count + "" + ChatColor.GRAY + ". /scrolls data view" + ChatColor.WHITE + "- will reload the plugins config file.\n");
        }
        player.sendMessage(message + "--------------------------------------------------\n");
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] arg) {
        Player player = (Player) cs;
        if (arg.length == 0) {
            sendHelp(player);
            return true;
        }
        switch (arg[0].toLowerCase()) {
            default: {
                sendHelp(player);
                return true;
            }
            case "help": {
                sendHelp(player);
                return true;
            }
            case "data": {
                if (arg.length > 1) {
                    if (arg[1].equalsIgnoreCase("save")) {
                        try {
                            File save = new File(plugin.getDataFolder() + "/test");
                            save.mkdir();
                            save = new File(plugin.getDataFolder() + "/test/" + System.nanoTime()+".txt");
                            FileWriter fw = new FileWriter(save);
                            for (ScrollType scrollType : ScrollType.values()) {
                                fw.write(scrollType.toString().toLowerCase() + " : \n");
                                for (DataTrackType trackType : DataTrackType.values()) {
                                    String loc = scrollType.toString().toLowerCase() + "." + trackType.toString().toLowerCase();
                                    if (plugin.getScrollListener().getData().containsKey(loc)) {
                                        fw.write("     " + trackType.toString().toLowerCase() + " : \n");
                                        switch (trackType) {
                                            case ENCHANTMENT: {
                                                Map<Enchantment, Integer> data = (HashMap<Enchantment, Integer>) plugin.getScrollListener().getData().get(loc);
                                                for (Enchantment ench : plugin.getScrollConfig().getEnchantmentPriority()) {
                                                    if(data.containsKey(ench)){
                                                        String s = "      %25s : %5s\n";
                                                        s= String.format(s, ench.getName(), data.get(ench));
                                                        System.out.println(s);
                                                        fw.write(s);
                                                    }
                                                }
                                                break;
                                            }
                                            case SUCCESS_RATE: {
                                                Map<Double, Integer> ratesMade = (Map<Double, Integer>) plugin.getScrollListener().getData().get(loc);
                                                for (double d : ratesMade.keySet()) {
                                                        String s = "      %5s : %5s\n";
                                                        fw.write(String.format(s, d, ratesMade.get(d)));
                                                }
                                                break;
                                            }
                                            case DESTROY_RATE: {
                                                Map<Double, Integer> ratesMade = (Map<Double, Integer>) plugin.getScrollListener().getData().get(loc);
                                                for (double d : ratesMade.keySet()) {
                                                        String s = "      %5s : %5s\n";
                                                        fw.write(String.format(s, d, ratesMade.get(d)));
                                                }
                                                break;
                                            }
                                            case TOTAL_MADE: {
                                                
                                                int made = (int) plugin.getScrollListener().getData().get(loc);
                                                String s = "    %5s\n";
                                                fw.write(String.format(s, made));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            fw.close();
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            cs.sendMessage("There was an error creating the data file!");
                            return true;
                        }
                    }
                }
                plugin.getScrollListener().toggleTracking(DataTrackType.SUCCESS_RATE);
                plugin.getScrollListener().toggleTracking(DataTrackType.DESTROY_RATE);
                plugin.getScrollListener().toggleTracking(DataTrackType.ENCHANTMENT);
                plugin.getScrollListener().toggleTracking(DataTrackType.TOTAL_MADE);
                cs.sendMessage("Toggled scroll tracking for " + DataTrackType.SUCCESS_RATE.toString() + ", " + DataTrackType.DESTROY_RATE.toString() + ", " + DataTrackType.ENCHANTMENT.toString() + ", " + DataTrackType.TOTAL_MADE.toString() + "\n"
                        );
                return true;
            }
            case "get": {
                if (!player.hasPermission("scrolls.command.get")) {
                    permissionCheck(player);
                    return true;
                }
                if (arg.length < 2) {
                    if (player.hasPermission("scrolls.command.get.more")) {
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
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.DARK_ENCH);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.DARK_ENCH);
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
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.BASIC_ENCH);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.BASIC_ENCH);
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
                                ItemStack s = scrollMath.createRandomScroll(ScrollType.CHAOS);
                                player.getInventory().addItem(s);
                                player.updateInventory();
                                ScrollSpawnedEvent spawnedEvent = new ScrollSpawnedEvent(s, false);
                                plugin.getPluginManager().callEvent(spawnedEvent);
                                return true;
                            }
                        }
                        while (i > 0) {
                            ItemStack s = scrollMath.createRandomScroll(ScrollType.CHAOS);
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
                if (!player.hasPermission("scrolls.command.reload")) {
                    permissionCheck(player);
                    return true;
                }
                plugin.reload();
                player.sendMessage("Success!");
            }
        }
        return false;
    }

}
