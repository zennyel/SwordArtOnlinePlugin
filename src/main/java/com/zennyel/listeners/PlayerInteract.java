package com.zennyel.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract  implements Listener {
    private long attackTime = 0;
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD) {
                attackTime = System.currentTimeMillis();
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD) {
                long holdTime = System.currentTimeMillis() - attackTime;
                if (holdTime >= 1000) { // 1 segundo de tempo de espera

                }
            }
        }
    }
}
