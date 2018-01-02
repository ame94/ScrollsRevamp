
package scrolls;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import scrolls.command.ScrollsCommand;
import scrolls.configuration.DataTrackType;
import scrolls.configuration.ScrollsConfig;
import scrolls.configuration.SpawnConfig;
import scrolls.configuration.scolls.BasicScrollConfig;
import scrolls.configuration.scolls.ChaosScrollConfig;
import scrolls.configuration.scolls.CleanSlateScrollConfig;
import scrolls.configuration.scolls.DarkScrollConfig;
import scrolls.event.EntityListener;
import scrolls.event.InventoryListener;
import scrolls.event.ScrollListener;
import scrolls.inventory.ScrollMath;
import scrolls.inventory.ScrollType;

/**
 *
 * @author knaxel
 */
public class ScrollsPlugin extends JavaPlugin {

    private ScrollsConfig config;
    private SpawnConfig spawnConfig;
    private BasicScrollConfig basicConfig;
    private DarkScrollConfig darkConfig;
    private CleanSlateScrollConfig cleanConfig;
    private ChaosScrollConfig chaosConfig;
    private PluginManager manager;
    private ScrollListener scrollListener;
    private ScrollMath scrollMath;

    @Override
    public void onEnable() {
        ScrollsPlugin.lg("Hello, thank you for trying the scrolls plugin!");
        
        ScrollsPlugin.lg("Loading configuration files...");
        config = new ScrollsConfig(this);
        spawnConfig = new SpawnConfig(this);
        basicConfig = new BasicScrollConfig(this);
        darkConfig = new DarkScrollConfig(this);
        cleanConfig = new CleanSlateScrollConfig(this);
        chaosConfig = new ChaosScrollConfig(this);
        scrollMath = new ScrollMath(config, basicConfig, darkConfig, chaosConfig, cleanConfig);
        
        if(config.isTrackData()){
            toggleTracking();
        }
        
        ScrollsPlugin.lg("Setting up event listeners...");
        manager = this.getServer().getPluginManager();
        manager.registerEvents(new InventoryListener(this, config), this);
        manager.registerEvents(new EntityListener(this, spawnConfig), this);
        
        
        this.getCommand("scrolls").setExecutor(new ScrollsCommand(this));
    }

    @Override
    public void onDisable() {
        ScrollsPlugin.lg("Goodbye and thank you! from the ScrollsPlugin!");
    }
    public ScrollMath getScrollMath(){
        return scrollMath;
    }
    public boolean isTrackingScrollData(){
        return scrollListener != null;
    }
    public void toggleTracking() {
        if (scrollListener == null) {
            scrollListener = new ScrollListener(config, basicConfig, darkConfig, chaosConfig, cleanConfig);
            manager.registerEvents(scrollListener, this);
        } else {
            HandlerList.unregisterAll(scrollListener);
            scrollListener = null;
        }
    }

    public PluginManager getPluginManager() {
        return manager;
    }

    public ScrollListener getScrollListener() {
        return this.scrollListener;
    }


    public static void lg(String arg) {
        Bukkit.getLogger().log(Level.INFO, arg);
    }

    public void reload() {
        ScrollsPlugin.lg("Reloading configurations...");
        config.reload();
        spawnConfig.reload();
        basicConfig.reload();
        darkConfig.reload();
        cleanConfig.reload();
        chaosConfig.reload();
        ScrollsPlugin.lg("Complete!");
    }

    public void saveDataFile() throws IOException {
        File save = new File(this.getDataFolder() + "/test");
        save.mkdir();
        save = new File(this.getDataFolder() + "/test/" + System.nanoTime() + ".txt");
        FileWriter fw = new FileWriter(save);
        for (ScrollType scrollType : ScrollType.values()) {
            fw.write(scrollType.toString().toLowerCase() + " : \n");
            for (DataTrackType trackType : DataTrackType.values()) {
                String loc = scrollType.toString() + "." + trackType.toString();
                if (scrollListener.getData().containsKey(loc)) {
                    fw.write("     " + trackType.toString().toLowerCase() + " : \n");
                    switch (trackType) {
                        case ENCHANTMENT: {
                            Map<Enchantment, Integer> data = (HashMap<Enchantment, Integer>) scrollListener.getData().get(loc);
                            for (Enchantment ench : config.getEnchantmentPriority()) {
                                if (data.containsKey(ench)) {
                                    String s = "      %25s : %5s\n";
                                    s = String.format(s, ench.getName(), data.get(ench));
                                    fw.write(s);
                                }
                            }
                            break;
                        }
                        case SUCCESS_RATE: {
                            Map<Double, Integer> ratesMade = (Map<Double, Integer>) scrollListener.getData().get(loc);
                            for (double d : ratesMade.keySet()) {
                                String s = "      %5s : %5s\n";
                                fw.write(String.format(s, d, ratesMade.get(d)));
                            }
                            break;
                        }
                        case DESTROY_RATE: {
                            Map<Double, Integer> ratesMade = (Map<Double, Integer>) scrollListener.getData().get(loc);
                            for (double d : ratesMade.keySet()) {
                                String s = "      %5s : %5s\n";
                                fw.write(String.format(s, d, ratesMade.get(d)));
                            }
                            break;
                        }
                        case TOTAL_MADE: {
                            int made = (int) scrollListener.getData().get(loc);
                            String s = "     %10s\n";
                            fw.write(String.format(s, made));
                            break;
                        }
                    }
                }
            }
        }
        fw.close();
    }

}
