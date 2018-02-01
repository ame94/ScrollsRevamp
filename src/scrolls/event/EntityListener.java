/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event;

import org.bukkit.entity.EntityType;
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

            // Witches drop dark scrolls 100% of the time
            if (event.getEntityType() == EntityType.WITCH) {
                ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.DARK);
                event.getDrops().add(scroll);
                ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll,true);
                plugin.getPluginManager().callEvent(e);
                return;
            }

            // Evokers drop both chaotic & dark 100% of the time
            if (event.getEntityType() == EntityType.EVOKER) {
                ItemStack scroll1 = plugin.getScrollMath().createRandomScroll(ScrollType.CHAOTIC);
                ItemStack scroll2 = plugin.getScrollMath().createRandomScroll(ScrollType.DARK);
                event.getDrops().add(scroll1);
                event.getDrops().add(scroll2);
                ScrollSpawnedEvent e1 = new ScrollSpawnedEvent(scroll1,true);
                ScrollSpawnedEvent e2 = new ScrollSpawnedEvent(scroll2,true);
                plugin.getPluginManager().callEvent(e1);
                plugin.getPluginManager().callEvent(e2);
                return;
            }

            // Vexes drop chaotic scrolls 1:6 of the time
            if (event.getEntityType() == EntityType.VEX) {
                if(r < (1.0/6.0)) {
                    ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.CHAOTIC);
                    event.getDrops().add(scroll);
                    ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll, true);
                    plugin.getPluginManager().callEvent(e);
                }
                return;
            }

            if (event.getEntityType() == EntityType.WOLF) {
                if(r < (spawnConfig.getChaosScrollRate()/3)) {
                    ItemStack scroll = plugin.getScrollMath().createRandomScroll(ScrollType.CHAOTIC);
                    event.getDrops().add(scroll);
                    ScrollSpawnedEvent e = new ScrollSpawnedEvent(scroll,true);
                    plugin.getPluginManager().callEvent(e);
                }
                return;
            }


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
