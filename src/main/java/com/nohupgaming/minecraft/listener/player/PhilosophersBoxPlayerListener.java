package com.nohupgaming.minecraft.listener.player;


import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerListener;

import com.nohupgaming.minecraft.PhilosophersBox;
import com.nohupgaming.minecraft.util.PhilosopherConstants;
import com.nohupgaming.minecraft.util.PhilosophersUtil;

public class PhilosophersBoxPlayerListener extends PlayerListener 
{
    private final PhilosophersBox _plugin;
    private enum Selection { NEXT, PREVIOUS };

    public PhilosophersBoxPlayerListener(final PhilosophersBox plugin)
    {
        _plugin = plugin;
    }
    
    @Override
    public void onItemHeldChange(PlayerItemHeldEvent event) 
    {
        Block target = event.getPlayer().getTargetBlock(null, 10); 
        if (target.getState() instanceof Sign)
        {
            Sign s = (Sign) target.getState();
            String l1 = s.getLine(0);
            String l2 = s.getLine(1);
            
            if ("Philosopher's".equals(l1) && "Box".equals(l2))
            {
                String l3 = s.getLine(2);
                String l4 = s.getLine(3);
                
                if (_plugin.getCurrentMaterial() != null)
                {
                    String compare = PhilosophersUtil.getMaterialName(_plugin.getCurrentMaterial());
                    if (!compare.equals(l3+l4))
                    {
                        setScreenText(s, compare);
                        return;
                    }
                }

                Material convertTo = null;
                List<String> keys = _plugin.getConversionKeys();
                
                if ((event.getNewSlot() == event.getPreviousSlot() + 1) || 
                    (event.getNewSlot() == 0 && event.getPreviousSlot() > 1))
                {
                    convertTo = determineType(_plugin.getCurrentMaterial(), keys, Selection.NEXT);
                }
                else
                {
                    convertTo = determineType(_plugin.getCurrentMaterial(), keys, Selection.PREVIOUS);
                }

                _plugin.setCurrentMaterial(convertTo);
                setScreenText(s, PhilosophersUtil.getMaterialName(convertTo));
            }                
        }
    }
    
    private Material determineType(Material current, List<String> types, Selection sel)
    {
        int idx = -1;
        if (current != null)
        {
            idx = types.indexOf(current.toString().toLowerCase());            
        }

        if (types.size() > 0)
        {
            if (idx == -1)
            {
               idx = 0;
            }
            else
            {
                if (sel.equals(Selection.NEXT))
                {
                    if (idx < (types.size() -1))
                    {
                        idx++;
                    }
                    else
                    {
                        idx = 0;
                    }
                }
                else if (sel.equals(Selection.PREVIOUS))
                {
                    if (idx == 0)
                    {
                        idx = types.size() - 1;
                    }
                    else
                    {
                        idx--;
                    }
                }
            }
            
            return Material.getMaterial(types.get(idx).toUpperCase());
        }
        
        return null;
    }
    
    private void setScreenText(Sign s, String text)
    {
        if (text == null)
        {
            s.setLine(2, "");
            s.setLine(3, "");
            
        }
        else if (text.length() > 15)
        {
            s.setLine(2, text.substring(0, 14));
            s.setLine(3, text.substring(14));
        }
        else
        {
            s.setLine(2, "");
            s.setLine(3, text);
        }
        
        s.update(true);
    }
}
