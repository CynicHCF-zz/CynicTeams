package com.cynichcf.hcf.nametag;

import org.bukkit.entity.Player;

public abstract class NametagProvider
{
    private String name;
    private int weight;
    
    public abstract NametagInfo fetchNametag(Player p0, Player p1);
    
    public static NametagInfo createNametag(String prefix, String suffix) {
        return NametagManager.getOrCreate(prefix, suffix);
    }
    
    public NametagProvider(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getWeight() {
        return this.weight;
    }
    
    protected static class DefaultNametagProvider extends NametagProvider
    {
        public DefaultNametagProvider() {
            super("Default Provider", 0);
        }
        
        @Override
        public NametagInfo fetchNametag(Player toRefresh, Player refreshFor) {
            return NametagProvider.createNametag("", "");
        }
    }
}
