/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrolls.event.scroll;

import javax.xml.stream.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author knaxel
 */
public class ChaosScrollUseEvent extends ScrollUseEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    
    private Enchantment[] enchs; 
    private final double v1,v2;

    public ChaosScrollUseEvent(ItemStack scroll, ItemStack appItem, int slot, Player player, Enchantment[] enchantmentPriority, double v1,double v2) {
        super(scroll, appItem, slot, player);
        this.enchs = enchantmentPriority;
        this.v1 = v1;
        this.v2=v2;
    }

    @Override
    public void apply() {
        ItemMeta appMeta = appItem.getItemMeta();
        
        int count = appMeta.getEnchants().size();
        
        appMeta.getEnchants().keySet().forEach((ench) -> {
            appMeta.removeEnchant(ench);
        });
        for (int i = 0; i < count; i++) {
            double f = Math.pow(Math.random(), v1);
            double l = Math.pow(Math.random(), v2);
            int max = enchs[((int) f * 10)].getMaxLevel();
            appMeta.addEnchant(enchs[(int) (f * (enchs.length-1))], (int) ((l * (max - 1)) + 1), true);
        }
        appItem.setItemMeta(appMeta);
        scroll.setAmount(scroll.getAmount() - 1);
        
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5, 1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 10, .75f);

    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
