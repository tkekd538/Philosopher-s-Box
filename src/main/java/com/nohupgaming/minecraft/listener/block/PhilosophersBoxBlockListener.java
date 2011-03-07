package com.nohupgaming.minecraft.listener.block;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;

import com.nohupgaming.minecraft.ContentConverter;
import com.nohupgaming.minecraft.PhilosophersBox;
import com.nohupgaming.minecraft.util.PhilosopherConstants;
import com.nohupgaming.minecraft.util.PhilosophersUtil;

public class PhilosophersBoxBlockListener extends BlockListener 
{
    private final PhilosophersBox _plugin;
    
    public PhilosophersBoxBlockListener(final PhilosophersBox plugin)
    {
        _plugin = plugin;
    }
    
    @Override
    public void onBlockInteract(BlockInteractEvent event) 
    {        
        if (event.getBlock().getType().equals(Material.STONE_BUTTON))
        {
            Chest c = PhilosophersUtil.findPhilosophersBox(event.getBlock());
            Player pl = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
            Material m = _plugin.getCurrentMaterial();
            
            if (m == null && c != null)
            {
                if (pl != null)
                {
                    pl.sendMessage(ChatColor.YELLOW + "Please select a conversion at the sign first.");
                }
                
                return;
            }
            
            if (m != null && c != null)
            {
                String path = PhilosopherConstants.PHILOSOPHERS_PREFIX + 
                PhilosopherConstants.MATERIAL_PREFIX + m.toString().toLowerCase();
            
                if (PhilosophersUtil.hasPermission(_plugin, pl, path))
                {
                    Thread t = new Thread(new ContentConverter(_plugin, c));
                    t.start();                
                }
                else
                {
                    if (pl != null)
                    {
                        pl.sendMessage(ChatColor.RED + "You do not have permission to execute this conversion.");
                    }
                }
            }
        }
    }
}
