package com.zennyel.listeners.player;

import com.zennyel.SAO;
import com.zennyel.player.Character;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerRegen implements Listener {
    Character character;
    @EventHandler
    public void onConsume(PlayerInteractEvent e){
        Player p = e.getPlayer();
        character = SAO.getPlugin(SAO.class).getPlayer(p.getUniqueId());
        int actualHealth = character.getActualHealth();

        if(p.getItemInHand() == null){
            return;
        }

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = e.getItem();

            List<Material> consumables = Arrays.asList(Material.BEEF, Material.APPLE);

            for(Material i : consumables){
                if(item.getType() != i){
                    return;
                }
            }

            if(actualHealth > character.getHealth()){
                item.subtract(1);
                int healthSom = (int) (actualHealth*0.5/100);
                character.setActualHealth(actualHealth + healthSom);
            }
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP,1, 1);
            p.sendMessage("§aVocê comeu e regenerou");
        }



    }

}
