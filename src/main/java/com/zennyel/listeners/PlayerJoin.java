package com.zennyel.listeners;

import com.zennyel.SAO;
import com.zennyel.player.Character;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        Character player = SAO.getPlugin(SAO.class).getPlayer(id);
        if(SAO.getPlugin(SAO.class).getSql().getPlayer(p.getUniqueId()) == null || SAO.getPlugin(SAO.class).getSql().getPlayer(p.getUniqueId()) == null){
            SAO.getPlugin(SAO.class).createCharacter(p);
        }
        SAO.getPlugin(SAO.class).loadCharacter(p);
        player.setActualHealth(player.getHealth());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        UUID id = p.getUniqueId();
        Character player = SAO.getPlugin(SAO.class).getPlayer(id);
        SAO.getPlugin(SAO.class).getSql().insertPlayer(player, id);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player p = event.getPlayer();
        UUID id = p.getUniqueId();
        Character player = SAO.getPlugin(SAO.class).getPlayer(id);
        SAO.getPlugin(SAO.class).getSql().insertPlayer(player, id);
        player.setActualHealth(player.getHealth());
    }






}
