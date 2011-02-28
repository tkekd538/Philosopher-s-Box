package com.nohupgaming.minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.nohupgaming.minecraft.util.PhilosophersUtil;
import com.nohupgaming.minecraft.util.ValuedItemStackComparator;

public class ContentConverter implements Runnable {

    private PhilosophersBox _plugin;
    private Chest _chest;
    private Material _primary;
    
    public ContentConverter(PhilosophersBox p, Chest c)
    {
        _plugin = p;
        _chest = c;
    }
    
    public void run() 
    {
        if (_chest != null)
        {
            ItemStack[] existing = _chest.getInventory().getContents().clone();
            _chest.getInventory().clear();
            Arrays.sort(existing, new ValuedItemStackComparator(_plugin));
            List<ItemStack> converted = new ArrayList<ItemStack>();
            
            for (ItemStack exists : existing)
            {
                System.out.println("Item: " + exists.getTypeId());
                //don't destroy anything that doesn't have a defined value
                if (!exists.getType().equals(Material.AIR) &&
                    _plugin.getMaterialValue(exists.getType()) == 0)
                {
                    System.out.println("Valueless: " + exists.getType());
                    converted.add(exists);
                }
            }
            
            int bankValue = PhilosophersUtil.calculateValue(_plugin, existing);
            System.out.println("Overall value is " + bankValue);
            if (bankValue >= _plugin.getMaterialValue(_plugin.getCurrentMaterial()))
            {                    
                Material convertTo = _plugin.getCurrentMaterial();
                
                while (bankValue > 0)
                {
                    bankValue = PhilosophersUtil.createItems(_plugin, convertTo, bankValue, converted);
                    Material nextType = null;
                    for (ItemStack exists : existing)
                    {
                        if (!exists.getType().equals(Material.AIR))
                        {
                            System.out.println("Existing, checking " + exists.getType());
                            if (bankValue >= _plugin.getMaterialValue(exists.getType()))
                            {
                                nextType = exists.getType();
                            }
                        }
                    }

                    System.out.println("Next type is " + nextType + " with a bank of " + bankValue);
                    
                    if (nextType == null)
                    {
                        break;
                    }
                    
                    convertTo = nextType;
                }
                
                for (ItemStack newstack : converted)
                {
                    _chest.getInventory().addItem(newstack);
                }
            }
            else
            {
                _chest.getInventory().setContents(existing);
            }
            
            _chest.update();
        }
    }
}
