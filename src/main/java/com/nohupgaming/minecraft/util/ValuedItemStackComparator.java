package com.nohupgaming.minecraft.util;

import java.util.Comparator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.nohupgaming.minecraft.PhilosophersBox;

public class ValuedItemStackComparator implements Comparator<ItemStack> 
{
    final PhilosophersBox _plugin;

    public ValuedItemStackComparator(final PhilosophersBox plugin)
    {
        super();
        _plugin = plugin;
    }
      
    public int compare(ItemStack o1, ItemStack o2) 
    {
        Integer val1 = o1.getType().equals(Material.AIR) ? new Integer(0) : _plugin.getMaterialValue(o1.getType());
        Integer val2 = o2.getType().equals(Material.AIR) ? new Integer(0) : _plugin.getMaterialValue(o2.getType());
        
        return val1.compareTo(val2);
    }

}
