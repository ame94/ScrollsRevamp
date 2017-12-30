
package scrolls;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import scrolls.command.ScrollsCommand;
import scrolls.configuration.ScrollsConfig;
import scrolls.configuration.SpawnConfig;
import scrolls.configuration.scolls.BasicScrollConfig;
import scrolls.configuration.scolls.ChaosScrollConfig;
import scrolls.configuration.scolls.CleanSlateScrollConfig;
import scrolls.configuration.scolls.DarkScrollConfig;
import scrolls.event.InventoryListener;
import scrolls.event.ScrollListener;
import scrolls.inventory.ScrollMath;

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
    
    private  PluginManager manager;
    
    private ScrollListener scrollListener;
    
    private ScrollMath scrollMath;

    @Override
    public void onEnable() {
        ScrollsPlugin.lg("Hello, thank you for trying the scrolls plugin!");
        ScrollsPlugin.lg("Loading configuration files");
        config = new ScrollsConfig(this);
        spawnConfig = new SpawnConfig(this);
        
        basicConfig = new BasicScrollConfig(this);
        darkConfig = new DarkScrollConfig(this);
        cleanConfig = new CleanSlateScrollConfig(this);
        chaosConfig = new ChaosScrollConfig(this);
        
        scrollMath = new ScrollMath(config,basicConfig,darkConfig,chaosConfig,cleanConfig);
        
        scrollListener = new ScrollListener(config,basicConfig,darkConfig,chaosConfig,cleanConfig);
        
        manager =  this.getServer().getPluginManager();
        
        manager.registerEvents(scrollListener,this);
        manager.registerEvents(new InventoryListener(this), this);
        this.getCommand("scrolls").setExecutor(new ScrollsCommand(this,scrollMath));
        
    }

    @Override
    public void onDisable() {
        ScrollsPlugin.lg("Goodbye and thank you! from the ScrollsPlugin!");
    }
    public PluginManager getPluginManager(){
        return manager;
    }
public ScrollListener getScrollListener(){
    return this.scrollListener;
}
public ScrollsConfig getScrollConfig(){
    return config;
}
    public static void lg(String arg) {
        Bukkit.getLogger().log(Level.INFO, arg);
    }
    public void reload() {
        ScrollsPlugin.lg("Reloading configurations...");
        config.reload();
        spawnConfig.reload();
    }

}
