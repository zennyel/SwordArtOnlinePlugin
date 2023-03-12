package com.zennyel.listeners;

import com.zennyel.SAO;
import com.zennyel.events.StatsLevelUpEvent;
import com.zennyel.player.Character;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StatsLevelUp implements Listener {

    @EventHandler
    public void onLevelUp(StatsLevelUpEvent event){
        Player p = event.getPlayer();
        Character character = event.getCharacter();

        int dex = character.getDexterity();
        int str = character.getStrength();

        p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(1 + dex);
        p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1 + str/2);
        character.setActualHealth(character.getHealth());
    }

}
