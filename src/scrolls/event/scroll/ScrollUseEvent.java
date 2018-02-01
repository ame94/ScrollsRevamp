/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event.scroll;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import scrolls.ScrollsPlugin;
import scrolls.inventory.ScrollType;


/**
 *
 * @author knaxel
 */
public abstract class ScrollUseEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    
    protected final ItemStack scroll;
    protected final ItemStack appItem;
    protected final Player player;
    protected final int slot;
    protected final boolean success, destroyed;
    protected final double successProb;
    protected final double destroyProb;

    public ScrollUseEvent(ItemStack scroll, ItemStack appItem, int slot, Player player) {
        this.scroll = scroll;
        this.appItem = appItem;
        this.player = player;
        this.slot = slot;
        
        ItemMeta meta = scroll.getItemMeta();
        List<String> lore = meta.getLore();
        ItemMeta appMeta = appItem.getItemMeta();

        Pattern pattern_successRate = Pattern.compile("Success Rate: (\\d+)");
        Matcher m = pattern_successRate.matcher(lore.get(1));

        if (m.find()) {
            successProb = (double)Integer.parseInt(m.group(1)) / 100.0;
            //ScrollsPlugin.lg("Using " + successProb);
        } else {
            successProb = 90.0;
        }

        if (Math.random() < successProb) {
            success = true;
            destroyed = false;
            destroyProb=0;
            apply();
        } else {
            success = false;
            if (lore.get(2).substring(0, 13).equalsIgnoreCase("Destroy Rate: ")) {
                destroyProb = Integer.parseInt(lore.get(2).substring(14, 16).replace(" ", ""));
                if (Math.random() < destroyProb) {
                    destroy();
                    destroyed = true;
                }else{
                    destroyed = false;
                }
            }else{
                destroyProb=0;
                destroyed = false;
            }
        }
        scroll.setAmount(0);
        player.updateInventory();
    }

    protected abstract void apply();

    private void destroy() {
        appItem.setAmount(0);
    }
    public double getDestroyRate(){
        return destroyProb;
    }
    public double getSuccessRate() {
        return successProb;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return scroll.getEnchantments();
    }

    public ScrollType getType() {
        return ScrollType.valueOf(scroll.getItemMeta().getLore().get(0).toUpperCase());
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getScroll() {
        return scroll;
    }

    public ItemStack getAppItem() {
        return appItem;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
