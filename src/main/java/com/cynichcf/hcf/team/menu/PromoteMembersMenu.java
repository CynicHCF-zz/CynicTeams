package com.cynichcf.hcf.team.menu;

import com.cynichcf.hcf.team.menu.button.ChangePromotionStatusButton;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class PromoteMembersMenu extends Menu {

    @NonNull @Getter Team team;


    public String getTitle(Player player) {
        return "Members of " + team.getName();
    }


    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        for (UUID uuid : team.getMembers()) {
            if (!team.isOwner(uuid) && !team.isCoLeader(uuid)) {
                buttons.put(index, new ChangePromotionStatusButton(uuid, team, true));
                index++;
            }
        }

        return buttons;
    }


    public boolean isAutoUpdate() {
        return true;
    }

}
