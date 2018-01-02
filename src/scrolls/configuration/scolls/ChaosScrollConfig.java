/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.configuration.scolls;

import org.bukkit.Material;
import scrolls.ScrollsPlugin;
import scrolls.configuration.ScrollConfig;
import scrolls.configuration.ScrollDataType;

/**
 *
 * @author knaxel
 */
public class ChaosScrollConfig extends ScrollConfig {
    
    
    public ChaosScrollConfig(ScrollsPlugin plugin) {
        super(plugin, "chaos");
        
        reload();
    }
    
    @Override
    public void setDefaults() {
        config.set(ScrollDataType.NAME.toString(), "'&5A chaos scroll with a %SUCCESS% % chance of success!'");
        config.set(ScrollDataType.DESCRIPTION.toString(), "'&dThis contains mystical powers that randomize the enchantment type and level on the item (will not add or remove)!'");
        config.set(ScrollDataType.DESTROY_DESCRIPTION.toString(), "'&cIf the scroll fails there is a %DESTROY% % chance of the item it was used on being destroyed.'");
        config.set(ScrollDataType.MATERIAL.toString(), Material.MAP.toString());
        config.set(ScrollDataType.PROBABILITY_INCREMENT.toString(), 10);
        config.set(ScrollDataType.SUCCESS_MAX.toString(), 60);
        config.set(ScrollDataType.SUCCESS_MIN.toString(), 60);
        config.set(ScrollDataType.DESTROY_MAX.toString(), 60);
        config.set(ScrollDataType.DESTROY_MIN.toString(), 30);
    }
    

    
}
