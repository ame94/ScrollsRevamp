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
public class DarkScrollConfig extends ScrollConfig {
    
    
    public DarkScrollConfig(ScrollsPlugin plugin) {
        super(plugin, "dark");
        
        reload();
    }
    
    @Override
    public void setDefaults() {
        config.set(ScrollDataType.NAME.toString(), "'&4A dark scroll for %ENCH% !'");
        config.set(ScrollDataType.DESCRIPTION.toString(), "'&6This contains mystical powers that give it the chance of enchanting the item it is used on!'");
        config.set(ScrollDataType.DESTROY_DESCRIPTION.toString(), "'&cIf the scroll fails there is a %DESTROY% % chance of the item it was used on being destroyed.'");
        config.set(ScrollDataType.MATERIAL.toString(), Material.MAP.toString());
        config.set(ScrollDataType.PROBABILITY_INCREMENT.toString(), 10);
        config.set(ScrollDataType.SUCCESS_MAX.toString(), 100);
        config.set(ScrollDataType.SUCCESS_MIN.toString(), 10);
        config.set(ScrollDataType.DESTROY_MAX.toString(), 60);
        config.set(ScrollDataType.DESTROY_MIN.toString(), 30);
    }
    

    
}
