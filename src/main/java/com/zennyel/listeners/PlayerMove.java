package com.zennyel.listeners;

import com.zennyel.SAO;
import com.zennyel.player.Character;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class PlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Character character = SAO.getPlugin(SAO.class).getPlayer(p.getUniqueId());

        int agi = character.getAgility();

        if(character == null){
            return;
        }

        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 + agi/200);

        if(e.getPlayer().isSprinting()){
            p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 + agi/60);
        }


    }

}
