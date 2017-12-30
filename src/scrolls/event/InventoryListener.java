/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import scrolls.ScrollsPlugin;
import scrolls.configuration.ScrollDataType;
import scrolls.event.scroll.BasicScrollUseEvent;
import scrolls.event.scroll.ChaosScrollUseEvent;
import scrolls.event.scroll.CleanSlateScrollUseEvent;
import scrolls.event.scroll.DarkScrollUseEvent;
import scrolls.event.scroll.ScrollUseEvent;
import scrolls.inventory.ScrollType;

/**
 *
 * @author knaxel
 */
public class InventoryListener implements Listener {

    private ScrollsPlugin plugin;

    public InventoryListener(ScrollsPlugin plugin) {
        this.plugin = plugin;
    }

    private void permissionCheck(Player player) {
        player.sendMessage(ChatColor.RED + "You don't have permission to use that!");
        player.closeInventory();
    }

    private boolean useScrollSlot(ItemMeta appMeta) {
        if (!appMeta.hasLore()) {
            List<String> appLore = new ArrayList<String>();
            appLore.add(String.format("Scroll Slots: [%2s/%2s]", plugin.getScrollConfig().getScrollSlots()-1, plugin.getScrollConfig().getScrollSlots()));
            appMeta.setLore(appLore);
        } else {
            if (appMeta.getLore().contains(String.format("Scroll Slots: [ 0/%2s]", plugin.getScrollConfig().getScrollSlots()))) {
                return false;
            }
            List<String> appLore = appMeta.getLore();
            int slots = Integer.parseInt(appMeta.getLore().get(0).substring(15, 17).replace(" ", "")) - 1;
            appLore.set(0, String.format("Scroll Slots: [%2s/%2s]", slots, plugin.getScrollConfig().getScrollSlots()));
            appMeta.setLore(appLore);
        }
        return true;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if ((event.getAction() != InventoryAction.PLACE_ALL && event.getAction() != InventoryAction.SWAP_WITH_CURSOR) || event.getCursor().getType() == Material.AIR || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        ItemStack scroll = event.getCursor();
        ItemMeta scrollMeta = scroll.getItemMeta();
        if (!scrollMeta.hasLore() || scrollMeta.getLore().size() < 2 || !scrollMeta.getLore().get(1).contains("Success Rate: ")) {
            return;
        }
        ItemStack appItem = event.getCurrentItem();
        ItemMeta appMeta = appItem.getItemMeta();
        if (appMeta.hasLore() && appMeta.getLore().size() > 2 && (appMeta.getLore().get(1).contains("Success Rate:") || appMeta.getLore().contains("Success Rate:"))) {
            return;
        }
        //we know we have a scroll after this
        if (!player.hasPermission("scrolls.use")) {
            permissionCheck(player);
            return;
        }
        switch (ScrollType.valueOf(scrollMeta.getLore().get(0).toUpperCase())) {
            case CLEANSLATE: {
                ScrollUseEvent useEvent = new CleanSlateScrollUseEvent(scroll, appItem, event.getSlot(), player);
                plugin.getServer().getPluginManager().callEvent(useEvent);
                break;
            }
            case BASIC_ENCH: {
                if (!useScrollSlot(appMeta)) {
                    return;
                }
                appItem.setItemMeta(appMeta);
                ScrollUseEvent useEvent = new BasicScrollUseEvent(scroll, appItem, event.getRawSlot(), player);
                plugin.getServer().getPluginManager().callEvent(useEvent);
                break;
            }
            case DARK_ENCH: {
                if (!useScrollSlot(appMeta)) {
                    return;
                }
                appItem.setItemMeta(appMeta);
                ScrollUseEvent useEvent = new DarkScrollUseEvent(scroll, appItem, event.getRawSlot(), player);
                plugin.getServer().getPluginManager().callEvent(useEvent);
                break;
            }
            case CHAOS: {
                if (!useScrollSlot(appMeta)) {
                    return;
                }
                appItem.setItemMeta(appMeta);
                ScrollUseEvent useEvent = new ChaosScrollUseEvent(scroll, appItem, event.getRawSlot(), player);
                plugin.getServer().getPluginManager().callEvent(useEvent);
                break;
            }
        }
    }

}
