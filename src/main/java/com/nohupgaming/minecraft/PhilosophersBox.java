package com.nohupgaming.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.nohupgaming.minecraft.listener.block.PhilosophersBoxBlockListener;
import com.nohupgaming.minecraft.listener.player.PhilosophersBoxPlayerListener;
import com.nohupgaming.minecraft.util.PhilosopherConstants;

public class PhilosophersBox extends JavaPlugin 
{
    private PhilosophersBoxPlayerListener _pl;
    private PhilosophersBoxBlockListener _bl;
    private Material _m;
    private HashMap<Material, Integer> _vals;
    private List<String> _ck;
    private PermissionHandler _permissions;
    private boolean _oponly;
    
    public PhilosophersBox()
    {
        _pl = new PhilosophersBoxPlayerListener(this);
        _bl = new PhilosophersBoxBlockListener(this);
        _vals = new HashMap<Material, Integer>();        
        _ck = null;
        _permissions = null;
        _oponly = false;
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
        for (String s : c.getKeys(PhilosopherConstants.MATERIAL))
        {
            Material m = Material.getMaterial(s.toUpperCase());
            int val = c.getInt(PhilosopherConstants.MATERIAL_PREFIX + s + 
                PhilosopherConstants.VALUE_SUFFIX, 0);
            _vals.put(m, new Integer(val));
        }
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Type.PLAYER_ITEM_HELD, _pl, Priority.Normal, this);
        pm.registerEvent(Type.BLOCK_INTERACT, _bl, Priority.Normal, this);

        if (pm.getPlugin(PhilosopherConstants.PERMISSIONS) != null)
        {
            Permissions perm = (Permissions) pm.getPlugin(Permissions.name);
            _permissions = perm.getHandler(); 
        }
        
        String n = c.getString(PhilosopherConstants.OPSONLY);
        _oponly = c.getBoolean(PhilosopherConstants.OPSONLY, false);
        
        System.out.println("PhilosophersBox has been enabled.");
    }
    
    protected void buildConfiguration() 
    {
        Configuration c = getConfiguration();
        if (c != null)
        {
            c.setProperty(PhilosopherConstants.OPSONLY, false);
            c.setProperty(PhilosopherConstants.MATERIAL_PREFIX +  
                Material.STONE.toString().toLowerCase() + 
                PhilosopherConstants.VALUE_SUFFIX, 1);
            c.setProperty(PhilosopherConstants.MATERIAL_PREFIX +  
                Material.GOLD_ORE.toString().toLowerCase() + 
                PhilosopherConstants.VALUE_SUFFIX, 64);
            c.setProperty(PhilosopherConstants.MATERIAL_PREFIX +  
                Material.DIAMOND_ORE.toString().toLowerCase() + 
                PhilosopherConstants.VALUE_SUFFIX, 128);
            
            c.setProperty(PhilosopherConstants.MATERIAL_PREFIX +  
                Material.STONE.toString().toLowerCase() + 
                PhilosopherConstants.TO_SUFFIX, false);
            c.setProperty(PhilosopherConstants.MATERIAL_PREFIX +  
                Material.GOLD_ORE.toString().toLowerCase() + 
                PhilosopherConstants.TO_SUFFIX, true);
            c.setProperty(PhilosopherConstants.MATERIAL_PREFIX +  
                Material.DIAMOND_ORE.toString().toLowerCase() + 
                PhilosopherConstants.TO_SUFFIX, false);

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
    
    public List<String> getConversionKeys()
    {
        if (_ck == null)
        {
            _ck = new ArrayList<String>();
            List<String> keys = getConfiguration().getKeys(
                PhilosopherConstants.MATERIAL);
            
            for (String s : keys)
            {
                if (getConfiguration().getBoolean(PhilosopherConstants.MATERIAL_PREFIX + s + PhilosopherConstants.TO_SUFFIX, false))
                {
                    _ck.add(s);
                }
            }            
        }
        
        return _ck;
    }
    
    public PermissionHandler getPermissionHandler()
    {
        return _permissions;
    }
    
    public boolean isOpsOnly()
    {
        return _oponly;
    }
    
}
