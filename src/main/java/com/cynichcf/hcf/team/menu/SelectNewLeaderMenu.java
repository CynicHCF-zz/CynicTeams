package com.cynichcf.hcf.team.menu;

import com.cynichcf.hcf.team.menu.button.MakeLeaderButton;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.cynichcf.hcf.team.Team;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import rip.lazze.libraries.util.UUIDUtils;
import org.bukkit.entity.Player;

import java.util.*;

@RequiredArgsConstructor
public class SelectNewLeaderMenu extends Menu {

    @NonNull @Getter Team team;


    public String getTitle(Player player) {
        return "Leader for " + team.getName();
    }


    public Map<Integer, Button> getButtons(Player player) {
        HashMap<Integer, Button> buttons = new HashMap<>();
        int index = 0;

        ArrayList<UUID> uuids = new ArrayList<>();
        uuids.addAll(team.getMembers());

        Collections.sort(uuids, (u1, u2) -> UUIDUtils.name(u1).toLowerCase().compareTo(UUIDUtils.name(u2).toLowerCase()));

        for (UUID u : uuids) {
            buttons.put(index, new MakeLeaderButton(u, team));
            index++;
        }

        return buttons;
    }


    public boolean isAutoUpdate() {
        return true;
    }

}
