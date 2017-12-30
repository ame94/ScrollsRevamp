/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event.scroll;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author knaxel
 */
public class ScrollEntityDropEvent extends ScrollSpawnedEvent{
    private static final HandlerList HANDLERS = new HandlerList();
    
    private final Entity entity, killer;
    
    public ScrollEntityDropEvent(ItemStack scroll, Entity entity, Entity killer){
        super(scroll,true);
        this.entity = entity;
        this.killer = killer;
    }

    public Entity getEntity() {
        return entity;
    }

    public Entity getKiller() {
        return killer;
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    
}
