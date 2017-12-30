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
public class BasicScrollConfig extends ScrollConfig {
    
    
    public BasicScrollConfig(ScrollsPlugin plugin) {
        super(plugin, "enchantment");
        
        reload();
    }
    
    @Override
    public void setDefaults() {
        config.set(ScrollDataType.NAME.toString(), "A scroll for %ENCH% with a %SUCCESS% % chance of success!");
        config.set(ScrollDataType.DESCRIPTION.toString(), "This contains mystical powers that give it the chance of enchanting the item it is used on!");
        config.set(ScrollDataType.DESTROY_DESCRIPTION.toString(), "If the scroll fails there is a %DESTROY% % chance of the item it was used on being destroyed.");
        config.set(ScrollDataType.MATERIAL.toString(), Material.PAPER.toString());
        config.set(ScrollDataType.PROBABILITY_INCREMENT.toString(), 10);
        config.set(ScrollDataType.SUCCESS_MAX.toString(), 100);
        config.set(ScrollDataType.SUCCESS_MIN.toString(), 10);
        config.set(ScrollDataType.DESTROY_MAX.toString(), 0);
        config.set(ScrollDataType.DESTROY_MIN.toString(), 0);
    }


    
}
