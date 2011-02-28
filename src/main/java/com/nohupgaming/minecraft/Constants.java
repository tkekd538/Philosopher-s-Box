package com.nohupgaming.minecraft;

import org.bukkit.Material;

public class Constants 
{

    public static final String VALUE_PREFIX = "material";
    public static final String VALUE_SUFFIX = "value";
    public static final String STONE_VALUE = VALUE_PREFIX + "." + 
        Material.STONE.toString().toLowerCase() + "." + VALUE_SUFFIX;
    public static final String GOLDORE_VALUE = VALUE_PREFIX + "." + 
        Material.GOLD_ORE.toString().toLowerCase() + "." + VALUE_SUFFIX; 
    public static final String DIAMONDORE_VALUE = VALUE_PREFIX + "." + 
        Material.DIAMOND_ORE.toString().toLowerCase() + "." + VALUE_SUFFIX; 
    
}
