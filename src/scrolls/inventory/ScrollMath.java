/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.inventory;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import scrolls.ScrollsPlugin;
import scrolls.configuration.ScrollConfig;
import scrolls.configuration.ScrollDataType;
import scrolls.configuration.ScrollsConfig;
import scrolls.configuration.scolls.BasicScrollConfig;
import scrolls.configuration.scolls.ChaosScrollConfig;
import scrolls.configuration.scolls.CleanSlateScrollConfig;
import scrolls.configuration.scolls.DarkScrollConfig;

/**
 *
 * @author knaxel
 */
public class ScrollMath {

    private final ScrollsConfig config;
    private final BasicScrollConfig basicConfig;
    private final DarkScrollConfig darkConfig;
    private final ChaosScrollConfig chaosConfig;
    private final CleanSlateScrollConfig cleanConfig;

    public ScrollMath(ScrollsConfig config, BasicScrollConfig basicConfig, DarkScrollConfig darkConfig, ChaosScrollConfig chaosConfig, CleanSlateScrollConfig cleanConfig) {
        this.config = config;
        this.basicConfig = basicConfig;
        this.darkConfig = darkConfig;
        this.chaosConfig = chaosConfig;
        this.cleanConfig = cleanConfig;
    }

    public ItemStack createRandomScroll(ScrollType type) {
        List<String> lore = new ArrayList<String>();
        ItemStack scroll;
        ItemMeta meta;
        ScrollConfig scrollConfig;
        Enchantment ench;
        int level;
        String enchName = "", romanLevel = "";
        switch (type) {
            default:
                scrollConfig = basicConfig;
                scroll = new ItemStack(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                meta = scroll.getItemMeta();
                break;
            case BASIC_ENCH:
                scrollConfig = basicConfig;
                scroll = new ItemStack(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                meta = scroll.getItemMeta();
                ench = getRandomEnchantment(config.getRareness_enchantments());
                level = 0;
                try{
                level = (int) (Math.pow(
                        Math.random(), 
                        config.getRareness_enchlevel()) * 
                        (ench.getMaxLevel() - 1)) + 1;
                }catch(NullPointerException e ){
                    e.printStackTrace();
                }
                meta.addEnchant(ench, level, true);
                enchName = ench.getName().toLowerCase().replace("_", "");
                romanLevel = integerToRoman(level);
                lore.add(0,  ScrollType.BASIC_ENCH.toString().toLowerCase());
                break;
            case DARK_ENCH:
                scrollConfig = darkConfig;
                scroll = new ItemStack(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                meta = scroll.getItemMeta();
                ench = getRandomEnchantment(config.getRareness_enchantments());
                level = 0;
                try{
                level = (int) (Math.pow(
                        Math.random(), 
                        config.getRareness_enchlevel()) * 
                        (ench.getMaxLevel() - 1)) + 1;
                }catch(NullPointerException e ){
                    e.printStackTrace();
                }
                meta.addEnchant(ench, level, true);
                enchName = ench.getName().toLowerCase().replace("_", "");
                romanLevel = integerToRoman(level);
                lore.add(0,  ScrollType.DARK_ENCH.toString().toLowerCase());
                break;
            case CHAOS:
                scrollConfig = chaosConfig;
                scroll = new ItemStack(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                meta = scroll.getItemMeta();
                meta = Bukkit.getItemFactory().getItemMeta(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                lore.add(0,  ScrollType.CHAOS.toString().toLowerCase());
                break;
            case CLEANSLATE:
                scrollConfig = cleanConfig;
                scroll = new ItemStack(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                meta = scroll.getItemMeta();
                meta = Bukkit.getItemFactory().getItemMeta(scrollConfig.getAsMaterial(ScrollDataType.MATERIAL));
                lore.add(0,  ScrollType.CLEANSLATE.toString().toLowerCase());
                break;
        }
        int successProb = chance(scrollConfig.getAsDouble(ScrollDataType.SUCCESS_MAX),
                scrollConfig.getAsDouble(ScrollDataType.SUCCESS_MIN),
                scrollConfig.getAsInt(ScrollDataType.PROBABILITY_INCREMENT),
                config.getRareness_successrate());
        String scrollName = scrollConfig.getAsString(ScrollDataType.NAME)
                .replace("%SUCCESS%", successProb + "")
                .replace("%ENCH%", enchName)
                .replace("%LVL%", romanLevel);
        String description = scrollConfig.getAsString(ScrollDataType.DESCRIPTION)
                .replace("%SUCCESS%", successProb + "")
                .replace("%ENCH%", enchName)
                .replace("%LVL%", romanLevel);
        lore.add(1,String.format("Success Rate: %2s %%",successProb));
        if (scrollConfig.getAsDouble(ScrollDataType.DESTROY_MAX) != 0) {
            int destroyProb = chance(scrollConfig.getAsDouble(ScrollDataType.DESTROY_MAX),
                    scrollConfig.getAsDouble(ScrollDataType.DESTROY_MIN),
                    scrollConfig.getAsInt(ScrollDataType.PROBABILITY_INCREMENT),
                    1-config.getRareness_destroyrate());
            scrollName = scrollName.replace("%DESTROY%", "" + destroyProb);
            description = description.replace("%DESTROY%", "" + destroyProb);
            description += "\n" + scrollConfig.getAsString(ScrollDataType.DESTROY_DESCRIPTION)
                    .replace("%SUCCESS%", successProb + "")
                    .replace("%ENCH%", enchName)
                    .replace("%LVL%", romanLevel)
                    .replace("%DESTROY%", destroyProb + "");
            lore.add(2, String.format("Destroy Chance: %2s %%",destroyProb));
        }
        meta.setDisplayName(scrollName);
        lore.add(description);
        meta.setLore(lore);
        scroll.setItemMeta(meta);
        return scroll;
    }

    private int chance(double max, double min, int increment, double e) {
        max = max / 100;
        min = min / 100;
        double base = 0;
        base = Math.random() * Math.pow(Math.random(), e);
        base = base * (max - min) + min;
        return (int) Math.floor(base * (100) / increment) * increment;
    }

    private Enchantment getRandomEnchantment(double e) {
        Enchantment[] enchs = config.getEnchantmentPriority();
        double f;
        if (e == 0) {
            f = 1-Math.random();
        } else {
            f = 1-Math.random()*Math.pow(Math.random(), e);
        }
        Enchantment ench = enchs[(int) Math.floor(f * (enchs.length-1))];
        return ench;
    }

    private String integerToRoman(int n) {
        String roman = "";
        int repeat;
        repeat = n / 1000;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "M";
        }
        n = n % 1000;
        repeat = n / 900;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "CM";
        }
        n = n % 900;
        repeat = n / 500;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "D";
        }
        n = n % 500;
        repeat = n / 400;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "CD";
        }
        n = n % 400;
        repeat = n / 100;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "C";
        }
        n = n % 100;
        repeat = n / 90;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "XC";
        }
        n = n % 90;
        repeat = n / 50;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "L";
        }
        n = n % 50;
        repeat = n / 40;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "XL";
        }
        n = n % 40;
        repeat = n / 10;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "X";
        }
        n = n % 10;
        repeat = n / 9;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "IX";
        }
        n = n % 9;
        repeat = n / 5;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "V";
        }
        n = n % 5;
        repeat = n / 4;
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "IV";
        }
        n = n % 4;
        repeat = n / 1; // or simply repeat=n or i<=n in the condition part of the loop
        for (int i = 1; i <= repeat; i++) {
            roman = roman + "I";
        }
        return roman;
    }

}
