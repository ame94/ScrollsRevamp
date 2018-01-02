
package scrolls.configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import scrolls.ScrollsPlugin;

/**
 *
 * @author knaxel
 */
public class ScrollsConfig extends Config {
    private boolean trackData;
    private boolean contrastEnchantmentAndRates; // better the enchant, lower the success rate / higher the destroy rate
    private double rareness_successrate; //curve determinant for success rates
    private double rareness_destroyrate; //curve determinant for success rates
    private double rareness_enchantments; //curve determinant for echantments
    private double rareness_enchlevel; //curve determinant for level
    private int scrollSlots;
    private final Enchantment[] enchantmentRarity;

    public ScrollsConfig(ScrollsPlugin plugin) {
        super(plugin, "scrolls_configuration");
        enchantmentRarity = new Enchantment[30];
        reload();
    }

    @Override
    public void setDefaults() {
        String[] ench = new String[30];
        ench[0] = Enchantment.DAMAGE_ALL.getName();
        ench[1] = Enchantment.SILK_TOUCH.getName();
        ench[2] = Enchantment.LOOT_BONUS_BLOCKS.getName();
        ench[3] = Enchantment.LOOT_BONUS_MOBS.getName();
        ench[4] = Enchantment.DURABILITY.getName();
        ench[5] = Enchantment.SWEEPING_EDGE.getName();
        ench[6] = Enchantment.KNOCKBACK.getName();
        ench[7] = Enchantment.FIRE_ASPECT.getName();
        ench[8] = Enchantment.MENDING.getName();
        ench[9] = Enchantment.THORNS.getName();
        ench[10] = Enchantment.ARROW_INFINITE.getName();
        ench[11] = Enchantment.ARROW_DAMAGE.getName();
        ench[12] = Enchantment.ARROW_KNOCKBACK.getName();
        ench[13] = Enchantment.ARROW_FIRE.getName();
        ench[14] = Enchantment.DAMAGE_UNDEAD.getName();
        ench[15] = Enchantment.DAMAGE_ARTHROPODS.getName();
        ench[16] = Enchantment.DIG_SPEED.getName();
        ench[17] = Enchantment.OXYGEN.getName();
        ench[18] = Enchantment.LUCK.getName();
        ench[19] = Enchantment.DEPTH_STRIDER.getName();
        ench[20] = Enchantment.WATER_WORKER.getName();
        ench[21] = Enchantment.FROST_WALKER.getName();
        ench[22] = Enchantment.LURE.getName();
        ench[23] = Enchantment.PROTECTION_ENVIRONMENTAL.getName();
        ench[24] = Enchantment.PROTECTION_FIRE.getName();
        ench[25] = Enchantment.PROTECTION_FALL.getName();
        ench[26] = Enchantment.PROTECTION_EXPLOSIONS.getName();
        ench[27] = Enchantment.PROTECTION_PROJECTILE.getName();
        ench[28] = Enchantment.VANISHING_CURSE.getName();
        ench[29] = Enchantment.BINDING_CURSE.getName();
        for (int i = 0; i < enchantmentRarity.length - 1; i++) {
            enchantmentRarity[i] = Enchantment.getByName(ench[i]);
        
        }
        config.set("track_scroll_data", false);
        config.set("rareness.contrast_enchantments_and_rates", false);
        
        config.set("rareness.successrate", .6);
        config.set("rareness.destroyrate", .6);
        config.set("rareness.enchantments", .6);
        config.set("rareness.enchantment_level", .6);
        config.set("default_scroll_slots", 7);
        config.set("enchantmentworthe", ench);
    }

    @Override
    public void loadToPlugin() {
        trackData = config.getBoolean("track_scroll_data");
        contrastEnchantmentAndRates = config.getBoolean("rareness.contrast_enchantments_and_rates");
        rareness_successrate = config.getDouble("rareness.successrate");
        rareness_destroyrate = config.getDouble("rareness.destroyrate");
        rareness_enchantments = config.getDouble("rareness.enchantments");
        rareness_enchlevel = config.getDouble("rareness.enchantment_level");
        scrollSlots = config.getInt("default_scroll_slots");
        
        List<String> list = config.getStringList("enchantmentworthe");
        for (int i = 0; i < list.size() - 1; i++) {
            enchantmentRarity[i] = Enchantment.getByName(list.get(i));
        }
    }

    public boolean isTrackData() {
        return trackData;
    }

    public void setTrackData(boolean trackData) {
        this.trackData = trackData;
    }
    public Enchantment[] getEnchantmentPriority(){
        return enchantmentRarity;
    }
    public boolean isContrastEnchantmentAndRates() {
        return contrastEnchantmentAndRates;
    }

    public void setContrastEnchantmentAndRates(boolean contrastEnchantmentAndRates) {
        this.contrastEnchantmentAndRates = contrastEnchantmentAndRates;
    }

    public double getRareness_successrate() {
        return rareness_successrate;
    }

    public void setRareness_successrate(double rareness_successrate) {
        this.rareness_successrate = rareness_successrate;
    }

    public double getRareness_destroyrate() {
        return rareness_destroyrate;
    }

    public void setRareness_destroyrate(double rareness_destroyrate) {
        this.rareness_destroyrate = rareness_destroyrate;
    }

    public double getRareness_enchantments() {
        return rareness_enchantments;
    }

    public void setRareness_enchantments(double rareness_enchantments) {
        this.rareness_enchantments = rareness_enchantments;
    }

    public double getRareness_enchlevel() {
        return rareness_enchlevel;
    }

    public void setRareness_enchlevel(double rareness_enchlevel) {
        this.rareness_enchlevel = rareness_enchlevel;
    }

    public int getScrollSlots() {
        return scrollSlots;
    }

    public void setScrollSlots(int scrollSlots) {
        this.scrollSlots = scrollSlots;
    }

}
