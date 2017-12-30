/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scrolls.configuration;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import scrolls.ScrollsPlugin;

/**
 *
 * @author knaxel
 */
public abstract class ScrollConfig extends Config {

    protected String name;
    protected Map<ScrollDataType, Object> settings;

    public ScrollConfig(ScrollsPlugin plugin, String name) {
        super(plugin, "scrolls/" + name + "_config");
        this.name = name;
        settings = new HashMap<>();
    }

    @Override
    public void loadToPlugin() {
        for (ScrollDataType d : ScrollDataType.values()) {
            if (config.contains(d.toString())) {
                settings.put(d, config.get(d.toString()));
            }
            ScrollsPlugin.lg("Loaded " + name + " scroll setting : " + d.toString() + " : " + config.get(d.toString()));
        }
    }
    public int getAsInt(ScrollDataType d){
        if(!settings.containsKey(d)){
            ScrollsPlugin.lg(d.toString() + " does not exist in " + name + " scroll settings");
        }
        try{
            return (int) settings.get(d);
        }catch(Exception e){
            ScrollsPlugin.lg(name + " scroll setting : \n" +"Could not cast " + settings.get(d).toString() + " into a int at " + d.toString() );
            return -1;
        }
    }
    public boolean getAsBoolean(ScrollDataType d){
        if(!settings.containsKey(d)){
            ScrollsPlugin.lg(d.toString() + " does not exist in " + name + " scroll settings");
            return false;
        }
        try{
            return (boolean) settings.get(d);
        }catch(Exception e){
            ScrollsPlugin.lg(name + " scroll setting : \n" +"Could not cast " + settings.get(d).toString() + " into a boolean at " + d.toString() );
            return false;
        }
    }
    public Double getAsDouble(ScrollDataType d) {
        if(!settings.containsKey(d)){
            ScrollsPlugin.lg(d.toString() + " does not exist in " + name + " scroll settings");
            return null;
        }
        try {
            return Double.parseDouble(settings.get(d).toString());
        } catch (NumberFormatException e) {
            ScrollsPlugin.lg(name + " scroll setting : \n" +"Could not parse " + settings.get(d).toString() + " into a double at " + d.toString() );
            return null;
        }
    }
    public String getAsString(ScrollDataType d) {
        if(!settings.containsKey(d)){
            ScrollsPlugin.lg(d.toString() + " does not exist in " + name + " scroll settings");
            return null;
        }
        try{
            return (String)settings.get(d);
        }catch(Exception e ){
            ScrollsPlugin.lg(name + " scroll setting : \n" +"Could not cast " + settings.get(d).toString() + " into a String at " + d.toString());
            return null;
        }
    }
    public Material getAsMaterial(ScrollDataType d){
        if(!settings.containsKey(d)){
            ScrollsPlugin.lg(d.toString() + " does not exist in " + name + " scroll settings");
            return null;
        }
        try{
            return Material.getMaterial(settings.get(d).toString());
        }catch(Exception e){
            ScrollsPlugin.lg(name + " scroll setting : \n" +"Could not find " + settings.get(d).toString() + " into a Material at " + d.toString());
            return Material.PAPER;
        }
    }

}
