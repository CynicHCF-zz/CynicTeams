package com.cynichcf.hcf.deathmessage.util;

import com.cynichcf.hcf.deathmessage.objects.Damage;

public class UnknownDamage extends Damage {

    public UnknownDamage(String damaged, double damage) {
        super(damaged, damage);
    }

    public String getDeathMessage() {
        return (wrapName(getDamaged()) + " died.");
    }

}