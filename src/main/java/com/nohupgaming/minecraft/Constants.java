package com.nohupgaming.minecraft;

import org.bukkit.Material;

public class Constants 
{

    public static final String MATERIAL = "material";
    public static final String MATERIAL_PREFIX = "material.";
    public static final String VALUE_SUFFIX = ".value";
    public static final String TO_SUFFIX = ".convertto";
    public static final String STONE_VALUE = MATERIAL_PREFIX +  
        Material.STONE.toString().toLowerCase() + VALUE_SUFFIX;
    public static final String GOLDORE_VALUE = MATERIAL_PREFIX +  
        Material.GOLD_ORE.toString().toLowerCase() + VALUE_SUFFIX; 
    public static final String DIAMONDORE_VALUE = MATERIAL_PREFIX +  
        Material.DIAMOND_ORE.toString().toLowerCase() + VALUE_SUFFIX; 
    public static final String STONE_TO = MATERIAL_PREFIX +  
        Material.STONE.toString().toLowerCase() +  TO_SUFFIX;
    public static final String GOLDORE_TO = MATERIAL_PREFIX +  
        Material.GOLD_ORE.toString().toLowerCase() +  TO_SUFFIX; 
    public static final String DIAMONDORE_TO = MATERIAL_PREFIX +  
        Material.DIAMOND_ORE.toString().toLowerCase() + TO_SUFFIX;     
}
