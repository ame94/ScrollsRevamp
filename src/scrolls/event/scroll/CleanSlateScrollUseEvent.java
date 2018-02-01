/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event.scroll;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author knaxel
 */
public class CleanSlateScrollUseEvent extends ScrollUseEvent{
    private static final HandlerList HANDLERS = new HandlerList();

    public CleanSlateScrollUseEvent(ItemStack scroll, ItemStack appItem,int slot, Player player) {
        super(scroll, appItem,slot, player);
        
        
        
    }
    @Override
    public void apply(){
        
        ItemMeta appMeta = appItem.getItemMeta();
        if (appMeta.getLore().size() > 0) {
            String string = appMeta.getLore().get(0);
            int slots = Integer.parseInt(string.substring(15, 17).replaceAll(" ", ""));

            appMeta.getLore().set(0, String.format(string.replaceFirst(string.substring(15, 17), "%2s"),slots++));
            appItem.setItemMeta(appMeta);
        }
    }
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
