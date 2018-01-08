/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import scrolls.ScrollsPlugin;
import scrolls.configuration.SpawnConfig;
import scrolls.event.scroll.ScrollSpawnedEvent;
import scrolls.inventory.ScrollType;

/**
 *
 * @author knaxel
 */
public class EntityListener implements Listener{
    
    private ScrollsPlugin plugin;
    private SpawnConfig spawnConfig;
    
    public EntityListener (ScrollsPlugin plugin , SpawnConfig spawnConfig){
        
        this.plugin = plugin;
        this.spawnConfig = spawnConfig;
    }
    
    
    @EventHandler
    public void onDeath(EntityDeathEvent event){
        
        if(spawnConfig.isDropper(event.getEntityType())){
            double r = Math.random();
            if(r < spawnConfig.getChaosScrollRate()){
                ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.CHAOTIC);
                event.getDrops().add(scroll);
                ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll,true);
                plugin.getPluginManager().callEvent(e);
                
            }
            if(r < spawnConfig.getCleanslateScrollRate()){
                ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.CLEANSLATE);
                event.getDrops().add(scroll);
                ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll,true);
                plugin.getPluginManager().callEvent(e);
            }
            if(r < spawnConfig.getScrollRate()){
                ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.BASIC);
                event.getDrops().add(scroll);
                ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll,true);
                plugin.getPluginManager().callEvent(e);
            }
            if(r < spawnConfig.getDarkScrollRate()){
                ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.DARK);
                event.getDrops().add(scroll);
                ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll,true);
                plugin.getPluginManager().callEvent(e);
            }
            
        }
        
    }
    
}
