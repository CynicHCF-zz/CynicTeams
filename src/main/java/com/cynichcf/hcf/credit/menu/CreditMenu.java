//package com.cynichcf.hcf.credit.menu;
//
//import com.cynichcf.hcf.credit.menu.button.*;
//import com.cynichcf.hcf.credit.menu.button.hcf.*;
//import lombok.AllArgsConstructor;
//import com.cynichcf.hcf.HCF;
//import org.bukkit.ChatColor;
//import org.bukkit.entity.Player;
//import rip.lazze.libraries.menu.Button;
//import rip.lazze.libraries.menu.Menu;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@AllArgsConstructor
//public class CreditMenu extends Menu {
//
//    {
//        setPlaceholder(true);
//        setAutoUpdate(true);
//    }
//
//
//    public String getTitle(Player player) {
//        return ChatColor.GOLD + "Charm Shop";
//    }
//
//
//
//    public Map<Integer, Button> getButtons(Player player) {
//        Map<Integer, Button> buttons = new HashMap<>();
//        if (HCF.getInstance().getMapHandler().isKitMap()) {
//            buttons.put(2, new SwitcherButton()); // 40 Credits
//            buttons.put(3, new GrapplingButton()); // 40 Credits
//            buttons.put(4, new GodButton()); // 35 Credits
//            buttons.put(5, new MoneyButton()); // 100 Credits
//            buttons.put(6, new KingCashButton()); // 100 Credits
//        } else {
//            buttons.put(2, new SwitcherButton()); // 40 Credits
//            buttons.put(3, new GrapplingButton()); // 40 Credits
//            buttons.put(4, new GodButton()); // 35 Credits
//            buttons.put(5, new MoneyButton()); // 100 Credits
//            buttons.put(6, new KingCashButton()); // 100 Credits
//        }
//
//        return buttons;
//    }
//}
