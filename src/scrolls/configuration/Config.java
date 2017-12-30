
package scrolls.configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import scrolls.ScrollsPlugin;
import scrolls.ScrollsPlugin;

/**
 *
 * @author knaxel
 */
public abstract class Config {

    protected ScrollsPlugin plugin;
    protected FileConfiguration config;
    private File configFile;
    private final String configName;

    public Config(ScrollsPlugin plugin, String configName) {
        this.plugin = plugin;
        this.configName = configName;
    }

    public void reload() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder() + "/" + configName + ".yml");
            config = YamlConfiguration.loadConfiguration(configFile);
        }
        if (!config.contains("scroll-config")) {
            config.set("scroll-config", true);
            setDefaults();
            this.save();
        }
        try {
            loadToPlugin();
        } catch (Exception e) {
            ScrollsPlugin.lg("There was a severe error in reading the config " + configName + "\n");
        }
    }

    public void save() {
        if ((config == null) || (configFile == null)) {
            return;
        }
        try {
            config.save(configFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    public abstract void setDefaults();

    public abstract void loadToPlugin();

}
