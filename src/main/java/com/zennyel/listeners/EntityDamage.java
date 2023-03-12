package com.zennyel.listeners;

import com.zennyel.SAO;
import com.zennyel.player.Character;
import com.zennyel.utils.TitleAPI;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!event.getEntity().getType().equals(EntityType.PLAYER)){
            return;
        }

        Player player = (Player) event.getEntity();

        Character character = SAO.getPlugin(SAO.class).getPlayer(player.getUniqueId());
        int con = character.getActualHealth();
        if(con > 20){
            player.setHealth(20);
            character.setActualHealth(character.getActualHealth() - (int) event.getDamage());
            return;
        }

        character.setActualHealth(character.getActualHealth() - (int) event.getDamage());
        player.setHealth(character.getActualHealth());

    }

}
