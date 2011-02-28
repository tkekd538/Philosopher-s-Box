package com.nohupgaming.minecraft.listener.block;


import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;

import com.nohupgaming.minecraft.ContentConverter;
import com.nohupgaming.minecraft.PhilosophersBox;
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
            
            if (c != null)
            {
                Thread t = new Thread(new ContentConverter(_plugin, c));
                t.start();                
            }
            else
            {
                System.out.println("Box not found.");
            }
        }
    }
}
