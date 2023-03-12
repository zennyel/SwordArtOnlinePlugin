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
    private SAO instance = SAO.getPlugin(SAO.class);

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(!event.getEntity().getType().equals(EntityType.PLAYER)){
            return;
        }

        Player player = (Player) event.getEntity();

        Character character = getInstance().getPlayer(player.getUniqueId());
        int con = character.getActualHealth();
        int dex = character.getDexterity();

        event.setDamage(event.getDamage()*dex*0.2/100);

        if(con > 20){
            player.setHealth(20);
            character.setActualHealth(character.getActualHealth() - (int) event.getDamage());
            return;
        }

        if(character.getActualHealth() - event.getDamage() < 0){
            character.setActualHealth(0);
        }

        character.setActualHealth(character.getActualHealth() - (int) event.getDamage());
        player.setHealth(character.getActualHealth());

    }

    public SAO getInstance() {
        return instance;
    }
}
