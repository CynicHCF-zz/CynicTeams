package com.cynichcf.hcf.commands.lff.menu;

import com.cynichcf.hcf.commands.lff.menu.buttons.LFFCompleteButton;
import com.cynichcf.hcf.commands.lff.menu.buttons.LFFKitButton;
import lombok.Getter;
import rip.lazze.libraries.menu.Button;
import rip.lazze.libraries.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LFFMenu extends Menu {

    @Getter
    private List<String> selected = new ArrayList<>();

    public LFFMenu() {
        super(ChatColor.BLUE + "Select your Classes");
        setAutoUpdate(true);
    }


    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttonMap = new HashMap<>();

        buttonMap.put(0, new LFFKitButton("Diamond", Material.DIAMOND_CHESTPLATE, this));
        buttonMap.put(1, new LFFKitButton("Bard", Material.GOLD_CHESTPLATE, this));
        buttonMap.put(2, new LFFKitButton("Rogue", Material.CHAINMAIL_CHESTPLATE, this));
        buttonMap.put(3, new LFFKitButton("Archer", Material.LEATHER_CHESTPLATE, this));
        buttonMap.put(4, new LFFKitButton("Miner", Material.DIAMOND_PICKAXE, this));
        buttonMap.put(5, new LFFKitButton("Base Bitch", Material.REDSTONE, this));
        buttonMap.put(8, new LFFCompleteButton(this));

        return buttonMap;
    }
}
