/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import scrolls.configuration.DataTrackType;
import static scrolls.configuration.DataTrackType.ENCHANTMENT;
import static scrolls.configuration.DataTrackType.SUCCESS_RATE;
import scrolls.configuration.ScrollsConfig;
import scrolls.configuration.scolls.BasicScrollConfig;
import scrolls.configuration.scolls.ChaosScrollConfig;
import scrolls.configuration.scolls.CleanSlateScrollConfig;
import scrolls.configuration.scolls.DarkScrollConfig;
import scrolls.event.scroll.ScrollSpawnedEvent;
import scrolls.inventory.ScrollType;

/**
 *
 * @author knaxel
 */
public class ScrollListener implements Listener {

    private final ScrollsConfig config;
    private final BasicScrollConfig basicConfig;
    private final DarkScrollConfig darkConfig;
    private final ChaosScrollConfig chaosConfig;
    private final CleanSlateScrollConfig cleanConfig;
    private Map<String, Object> data;

    public ScrollListener(ScrollsConfig config, BasicScrollConfig basicConfig, DarkScrollConfig darkConfig, ChaosScrollConfig chaosConfig, CleanSlateScrollConfig cleanConfig) {
        this.config = config;
        this.basicConfig = basicConfig;
        this.darkConfig = darkConfig;
        this.chaosConfig = chaosConfig;
        this.cleanConfig = cleanConfig;
        data = new HashMap<>();
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    @EventHandler
    public void spawned(ScrollSpawnedEvent e) {
        ScrollType t = e.getType();
        String loc = e.getType().toString() + "." ;
        if (t == ScrollType.BASIC || t == ScrollType.DARK) {
            Map<Enchantment, Integer> enchsMade;
            if (data.containsKey(loc+DataTrackType.ENCHANTMENT.toString())) {
                enchsMade = (HashMap<Enchantment, Integer>) data.get(loc+DataTrackType.ENCHANTMENT.toString());
                for (Enchantment enchant : e.getEnchantments().keySet()) {
                    if (enchsMade.containsKey(enchant)) {
                        enchsMade.put(enchant, enchsMade.get(enchant) + 1);
                    } else {
                        enchsMade.put(enchant, 1);
                    }
                }
            } else {
                enchsMade = new HashMap<>();
                for (Enchantment enchant : e.getEnchantments().keySet()) {
                    enchsMade.put(enchant, 1);
                }
            }
            data.put(loc+DataTrackType.ENCHANTMENT.toString(), enchsMade);
        }
        
        int made = 0;
        if(data.containsKey(loc+DataTrackType.TOTAL_MADE.toString()))
            made = (int) data.get(loc+DataTrackType.TOTAL_MADE.toString());
        made++;
        
        
        Map<Double, Integer> sRatesMade;
        if(data.containsKey(loc+DataTrackType.SUCCESS_RATE.toString())){
            sRatesMade = (HashMap<Double, Integer>) data.get(loc+DataTrackType.SUCCESS_RATE.toString());
        }else{
            sRatesMade = new HashMap<>();
        }
        if (sRatesMade.containsKey(e.getSuccessRate())) {
            sRatesMade.put(e.getSuccessRate(), sRatesMade.get(e.getSuccessRate()) + 1);
        } else {
            sRatesMade.put(e.getSuccessRate(), 1);
        }
        
        Map<Double, Integer> dRatesMade;
        if (data.containsKey(loc+DataTrackType.DESTROY_RATE.toString())) {
            dRatesMade = (HashMap<Double, Integer>) data.get(loc+DataTrackType.DESTROY_RATE.toString());
        } else {
            dRatesMade = new HashMap<>();
        }
        if (dRatesMade.containsKey(e.getDestroyRate())) {
            dRatesMade.put(e.getDestroyRate(), dRatesMade.get(e.getDestroyRate()) + 1);
        } else {
            dRatesMade.put(e.getDestroyRate(), 1);
        }
        data.put(loc+DataTrackType.SUCCESS_RATE, sRatesMade);
        data.put(loc+DataTrackType.DESTROY_RATE, dRatesMade);
        data.put(loc+DataTrackType.TOTAL_MADE, made);
    }

}
