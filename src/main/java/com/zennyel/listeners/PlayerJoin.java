package com.zennyel.listeners;

import com.zennyel.SAO;
import com.zennyel.database.MySQL;
import com.zennyel.player.Skill;
import com.zennyel.player.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(SAO.getPlugin(SAO.class).getSql().getPlayer(p.getUniqueId()) == null){
            SAO.getPlugin(SAO.class).createCharacter(p);
        }
        SAO.getPlugin(SAO.class).loadCharacter(p);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        UUID id = p.getUniqueId();
        com.zennyel.player.Player player = SAO.getPlugin(SAO.class).getPlayer(id);
        SAO.getPlugin(SAO.class).getSql().insertPlayer(player, id);
    }






}
