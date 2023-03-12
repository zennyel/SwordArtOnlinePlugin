package com.zennyel.listeners.player;

import com.zennyel.SAO;
import com.zennyel.database.CharactersLoad;
import com.zennyel.player.Character;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {
    private SAO instance = SAO.getPlugin(SAO.class);
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        new CharactersLoad(getInstance().getSql(), p);
        Character player = getInstance().getPlayer(id);
        player.setActualHealth(player.getHealth());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        UUID id = p.getUniqueId();
        Character player = getInstance().getPlayer(id);
        getInstance().getSql().insertPlayer(player, id);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player p = event.getPlayer();
        UUID id = p.getUniqueId();
        Character player = getInstance().getPlayer(id);
        getInstance().getSql().insertPlayer(player, id);
        player.setActualHealth(player.getHealth());
    }

    public SAO getInstance() {
        return instance;
    }

}
