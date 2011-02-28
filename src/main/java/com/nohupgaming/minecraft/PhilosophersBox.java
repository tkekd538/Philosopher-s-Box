package com.nohupgaming.minecraft;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nohupgaming.minecraft.listener.block.PhilosophersBoxBlockListener;
import com.nohupgaming.minecraft.listener.player.PhilosophersBoxPlayerListener;

public class PhilosophersBox extends JavaPlugin 
{
    PhilosophersBoxPlayerListener _pl;
    PhilosophersBoxBlockListener _bl;
    Material _m;
    HashMap<Material, Integer> _vals;
    
    public PhilosophersBox()
    {
        _pl = new PhilosophersBoxPlayerListener(this);
        _bl = new PhilosophersBoxBlockListener(this);
        _vals = new HashMap<Material, Integer>();
    }
    
    public void onDisable() 
    {
        System.out.println("PhilosophersBox has been disabled.");
    }

    public void onEnable() 
    {
        if (!getDataFolder().exists())
        {
            buildConfiguration();
        }
        
        Configuration c = getConfiguration();
        for (String s : c.getKeys(Constants.VALUE_PREFIX))
        {
            Material m = Material.getMaterial(s.toUpperCase());
            int val = c.getInt(Constants.VALUE_PREFIX + "." + s + "." + 
                Constants.VALUE_SUFFIX, 0);
            _vals.put(m, new Integer(val));
        }
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Type.PLAYER_ITEM_HELD, _pl, Priority.Normal, this);
        pm.registerEvent(Type.BLOCK_INTERACT, _bl, Priority.Normal, this);

        System.out.println("PhilosophersBox has been enabled.");
    }
    
    protected void buildConfiguration() 
    {
        Configuration c = getConfiguration();
        if (c != null)
        {
            c.setProperty(Constants.STONE_VALUE, 1);
            c.setProperty(Constants.GOLDORE_VALUE, 64);
            c.setProperty(Constants.DIAMONDORE_VALUE, 128);
            
            if (!c.save())
            {
                getServer().getLogger().warning("Unable to persist configuration files, changes will not be saved.");
            }
        }
    }
    
    public HashMap<Material, Integer> getMaterialValues()
    {
        return _vals;
    }
    
    public Integer getMaterialValue(Material m)
    {
        Integer i = _vals.get(m);
        return i != null ? i.intValue() : 0;
    }
    
    public void setCurrentMaterial(Material m)
    {
        _m = m;
    }
    
    public Material getCurrentMaterial()
    {
        return _m;
    }
    
}
