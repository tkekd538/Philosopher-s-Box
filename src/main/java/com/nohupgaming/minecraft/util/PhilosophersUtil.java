package com.nohupgaming.minecraft.util;

import java.util.List;
import java.util.StringTokenizer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.nohupgaming.minecraft.PhilosophersBox;

public class PhilosophersUtil 
{    
    public static Chest findPhilosophersBox(Block related)
    {
        for (BlockFace bf : BlockFace.values())
        {
            Block chest = related.getRelative(bf);
            
            if (chest.getType().equals(Material.CHEST))
            {
                Block below = chest.getRelative(BlockFace.DOWN);
                if (below.getType().equals(Material.REDSTONE_TORCH_ON))
                {
                    return (Chest) chest.getState();
                }
            }
        }
        
        return null;
    }    
    
    public static int calculateValue(PhilosophersBox plugin, ItemStack[] stacks)
    {
        int totalVal = 0;
        for (ItemStack stack : stacks)
        {
            if (!stack.getType().equals(Material.AIR))
            {
                totalVal += (stack.getAmount() * 
                    plugin.getMaterialValue(stack.getType()));
            }
        }
        
        return totalVal;
    }
    
    public static int createItems(PhilosophersBox plugin, Material m, int bank, List<ItemStack> created)
    {
        if (!m.equals(Material.AIR))
        {
            int toValue = plugin.getMaterialValue(m);
            int create = bank / toValue;
            int remain = bank % toValue;

            System.out.println("Create " + create + " " + m + " with a remainder of " + remain);
            
            if (create > 0)
            {
                created.add(new ItemStack(m, create));
            }
            
            return remain;
        }
        
        return 0;
    }
    
    public static String getMaterialName(Material m)
    {
        String out = null;
        StringTokenizer st = new StringTokenizer(m.toString(), "_");
        
        while (st.hasMoreTokens())
        {
            if (out != null)
            {
                out = out + " ";
                out = out + initCap(st.nextToken());
            }
            else
            {
                out = initCap(st.nextToken());
            }
        }
        
        return out;
    }
    
    public static Material getMaterialFromName(String s)
    {
        StringTokenizer st = new StringTokenizer(s, " ");
        String name = null;
        
        while (st.hasMoreTokens())
        {
            if (name != null)
            {
                name = name + "_";
                name = name + st.nextToken().toUpperCase();
            }
            else
            {
                name = st.nextToken().toUpperCase();
            }
        }
        
        return Material.getMaterial(name);        
    }

    public static String initCap(String s)
    {
        return (s != null) 
            ? s.substring(0,1).toUpperCase()
                + s.substring(1, s.length()).toLowerCase()
            : null; 
    }        
}
