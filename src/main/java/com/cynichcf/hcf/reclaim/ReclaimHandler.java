package com.cynichcf.hcf.reclaim;

import com.cynichcf.hcf.HCF;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReclaimHandler {

    public List<Group> getGroups(){
        List<Group> groups = new ArrayList<Group>();
        for (String s : HCF.getInstance().getReclaimConfig().getConfiguration().getConfigurationSection("groups").getKeys(false)){
            Group group = new Group();
            group.setName(s);
            group.setCommands(HCF.getInstance().getReclaimConfig().getConfiguration().getStringList("groups." + s + ".commands"));
            groups.add(group);
        }
        return groups;
    }

    public boolean hasReclaimed(Player player){
        return HCF.getInstance().getReclaimConfig().getConfiguration().getBoolean("reclaimed." + player.getUniqueId().toString());
    }

    public void setUsedReclaim(Player p, boolean used){
        HCF.getInstance().getReclaimConfig().getConfiguration().set("reclaimed." + p.getUniqueId().toString(), used);
        HCF.getInstance().getReclaimConfig().save();
    }
}
