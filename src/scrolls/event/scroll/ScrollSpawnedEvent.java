/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event.scroll;

import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import scrolls.inventory.ScrollType;

/**
 *
 * @author knaxel
 */
public class ScrollSpawnedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final ItemStack scroll;
    private final boolean natural;
    private double successProb;
    private double destroyProb;

    public ScrollSpawnedEvent(ItemStack scroll, boolean natural) {
        this.scroll = scroll;
        this.natural = natural;
        ItemMeta meta = scroll.getItemMeta();
        List<String> lore = meta.getLore();
        successProb = (double) Integer.parseInt(ChatColor.stripColor(lore.get(1)).substring(14, 16).replace(" ", "")) / 100;
        destroyProb = 0.0;
        if (lore.size() > 2 && ChatColor.stripColor(lore.get(2)).substring(0, 15).equalsIgnoreCase("Destroy Chance:")){
            destroyProb = (double) Integer.parseInt(ChatColor.stripColor(lore.get(2)).substring(16, 19).replace(" ", "")) / 100;
        }
    }
    public ScrollType getType() {
        return ScrollType.valueOf(scroll.getItemMeta().getLore().get(0).toUpperCase());
    }

    public double getDestroyRate() {
        return destroyProb;
    }

    public double getSuccessRate() {
        return successProb;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return scroll.getEnchantments();
    }

    public ItemStack getScroll() {
        return scroll;
    }

    public boolean isNatural() {
        return natural;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
