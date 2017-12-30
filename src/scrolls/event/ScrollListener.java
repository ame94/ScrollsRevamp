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
    private List<DataTrackType> tracking;
    private Map<String, Object> data;

    public ScrollListener(ScrollsConfig config, BasicScrollConfig basicConfig, DarkScrollConfig darkConfig, ChaosScrollConfig chaosConfig, CleanSlateScrollConfig cleanConfig) {
        this.config = config;
        this.basicConfig = basicConfig;
        this.darkConfig = darkConfig;
        this.chaosConfig = chaosConfig;
        this.cleanConfig = cleanConfig;
        data = new HashMap<>();
        tracking = new ArrayList<>();
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void toggleTracking(DataTrackType type) {
        if (!tracking.contains(type)) {
            tracking.add(type);
        } else {
            tracking.remove(type);
        }
    }

    @EventHandler
    public void spawned(ScrollSpawnedEvent e) {
        for (DataTrackType t : tracking) {
            String loc = e.getType().toString().toLowerCase() + "." + t.toString().toLowerCase();
            switch (t) {
                case ENCHANTMENT: {
                    if (e.getType() != ScrollType.BASIC_ENCH && e.getType() != ScrollType.DARK_ENCH) {
                        break;
                    }
                    Map<Enchantment, Integer> enchsMade;
                    if (data.containsKey(loc)) {
                        enchsMade = (HashMap<Enchantment, Integer>) data.get(loc);
                        
                        for(Enchantment enchant : e.getEnchantments().keySet()){
                            if(enchsMade.containsKey(enchant)){
                                enchsMade.put(enchant, enchsMade.get(enchant) + 1);
                            }else{
                                enchsMade.put(enchant, 1);
                            }
                        }
                    } else {
                        enchsMade = new HashMap<>();
                        for(Enchantment enchant : e.getEnchantments().keySet()){
                            enchsMade.put(enchant, 1);
                        }
                    }
            System.out.println(enchsMade.toString());
                    data.put(loc, enchsMade);
                        break;
                }
                case SUCCESS_RATE: {
                    Map<Double, Integer> ratesMade;
                    if (data.containsKey(loc)) {
                        ratesMade = (HashMap<Double, Integer>) data.get(loc);
                    } else {
                        ratesMade = new HashMap<>();
                    }
                    if (ratesMade.containsKey(e.getSuccessRate())) {
                        ratesMade.put(e.getSuccessRate(), ratesMade.get(e.getSuccessRate()) + 1);
                    } else {
                        ratesMade.put(e.getSuccessRate(), 1);
                    }
                    data.put(loc, ratesMade);
                        break;
                }
                case DESTROY_RATE: {
                    Map<Double, Integer> ratesMade;
                    if (data.containsKey(loc)) {
                        ratesMade = (HashMap<Double, Integer>) data.get(loc);
                    } else {
                        ratesMade = new HashMap<>();
                    }
                    if (ratesMade.containsKey(e.getDestroyRate())) {
                        ratesMade.put(e.getDestroyRate(), ratesMade.get(e.getDestroyRate()) + 1);
                    } else {
                        ratesMade.put(e.getDestroyRate(), 1);
                    }
                    data.put(loc, ratesMade);
                    break;
                }
                case TOTAL_MADE: {
                    int made = 0;
                    if (data.containsKey(loc)) {
                        made = (int) data.get(loc);
                    }
                    made++;
                    data.put(loc, made);
                    break;
                }
            }
        }
    }

}
