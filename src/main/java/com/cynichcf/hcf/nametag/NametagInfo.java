package com.cynichcf.hcf.nametag;

import rip.lazze.libraries.packet.ScoreboardTeamPacketMod;

import java.util.ArrayList;

public class NametagInfo
{
    private ScoreboardTeamPacketMod teamAddPacket;
    private String name;
    private String prefix;
    private String suffix;

	protected NametagInfo(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.teamAddPacket = new ScoreboardTeamPacketMod(name, prefix, suffix, new ArrayList(), 0);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof NametagInfo) {
            NametagInfo otherNametag = (NametagInfo)other;
            return this.name.equals(otherNametag.name) && this.prefix.equals(otherNametag.prefix) && this.suffix.equals(otherNametag.suffix);
        }
        return false;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public ScoreboardTeamPacketMod getTeamAddPacket() {
        return teamAddPacket;
    }
}
