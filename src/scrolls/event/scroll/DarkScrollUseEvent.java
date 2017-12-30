 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event.scroll;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author knaxel
 */
public class DarkScrollUseEvent extends ScrollUseEvent{
    private static final HandlerList HANDLERS = new HandlerList();

    public DarkScrollUseEvent(ItemStack scroll, ItemStack appItem,int slot, Player player) {
        super(scroll, appItem,slot, player);
    }
    
    public void apply(){
        
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    
    
}
