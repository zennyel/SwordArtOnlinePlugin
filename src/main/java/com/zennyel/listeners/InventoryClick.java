package com.zennyel.listeners;

import com.zennyel.SAO;
import com.zennyel.menu.StatsMenu;
import com.zennyel.player.Character;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryClick implements Listener {
    private SAO instance = SAO.getPlugin(SAO.class);
    Inventory statsMenu = Bukkit.createInventory(null, 27, "§6Menu de Status");

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Character p = getInstance().getPlayer(player.getUniqueId());

        if (event.getView().getTitle().equalsIgnoreCase("§6Menu de Status")) {
            if (event.getCurrentItem() == null) {
                return;
            }

            Material itemType = event.getCurrentItem().getType();
            if(itemType.equals(Material.PLAYER_HEAD)){
                List<String> lore = new ArrayList<>();
                lore.add("§5§lSEUS STATUS");
                lore.add("§5§l____________");
                lore.add("                ");
                lore.add("§6LEVEL: " + (int) p.getLevel());
                lore.add("§6FORÇA: " + p.getStrength());
                lore.add("§6AGILIDADE: "+ p.getAgility());
                lore.add("§6DESTREZA: " + p.getDexterity());
                lore.add("§6VIDA: " + p.getHealth());
                lore.add("§6PONTOS: " + p.getPoints());
                lore.add("§5§l____________");
                for(String s : lore){
                    player.sendMessage(s);
                }
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            }

            if (p.getPoints() <= 0 && itemType != Material.PLAYER_HEAD) {
                player.sendMessage("§4Pontos Insuficientes!");
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                return;
            }

            switch (itemType) {
                case DIAMOND_SWORD:
                    p.upStrength(player);
                    player.sendMessage("§6Parabéns, você melhorou sua força!");
                    break;
                case SHIELD:
                    p.upDexterity(player);
                    player.sendMessage("§6Parabéns, você melhorou sua destreza!");
                    break;
                case LEATHER_BOOTS:
                    p.upAgility(player);
                    player.sendMessage("§6Parabéns, você melhorou sua agilidade!");
                    break;
                case GOLDEN_APPLE:
                    p.upHealth(player);
                    player.sendMessage("§6Parabéns, você melhorou sua vida!");
                    break;
            }

            p.removePoints(1);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 10);
            event.setCancelled(true);
        }

        if(event.getView().getTitle().equalsIgnoreCase("§6Menu Principal")){
            if (event.getCurrentItem() == null) {
                return;
            }

            Material itemType = event.getCurrentItem().getType();
            switch (itemType){
                case PLAYER_HEAD:
                    new StatsMenu(statsMenu, p);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    player.openInventory(statsMenu);
                    break;
                case NETHERITE_SWORD:
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    break;
            }
            event.setCancelled(true);
        }




    }

    public SAO getInstance() {
        return instance;
    }
}