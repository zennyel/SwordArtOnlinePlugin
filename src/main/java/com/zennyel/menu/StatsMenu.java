package com.zennyel.menu;
import com.zennyel.player.Player;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StatsMenu {
    private final Inventory inventory;

    public StatsMenu(Inventory inventory, com.zennyel.player.Player player) {
        this.inventory = inventory;
        addItems(player);
    }

    private void addItems(Player player) {
        addCenteredItem(new ItemStack(Material.DIAMOND_SWORD), "§4§lFORÇA", "§7Clique para melhorar!", 1 + 9);
        addCenteredItem(new ItemStack(Material.SHIELD), "§8§lDESTREZA", "§7Clique para melhorar!", 3 + 9);
        addCenteredItem(new ItemStack(Material.GOLDEN_APPLE), "§a§lVIDA", "§7Clique para melhorar!", 5 + 9);
        addCenteredItem(new ItemStack(Material.LEATHER_BOOTS), "§e§lAGILIDADE", "§7Clique para melhorar!", 7 + 9);
        List<String> lore = new ArrayList<>();
        lore.add("§7§lLEVEL: " + player.getLevel());
        lore.add("§7FORÇA: " + player.getStrength());
        lore.add("§7AGILIDADE: "+ player.getAgility());
        lore.add("§7DESTREZA: " + player.getDexterity());
        lore.add("§7VIDA: " + player.getHealth());
        lore.add("§7PONTOS: " + player.getPoints());
        addCenteredItem(new ItemStack(Material.PLAYER_HEAD), "§5§lPERFIL", lore, 26);
    }

    private void addCenteredItem(ItemStack itemStack, String displayName, String lore, int slot) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);

        List<String> loreLines = new ArrayList<>();
        loreLines.add(lore);
        meta.setLore(loreLines);
        itemStack.setItemMeta(meta);

        inventory.setItem(slot, itemStack);
    }

    private void addCenteredItem(ItemStack itemStack, String displayName, List<String> lore, int slot) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);

        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        inventory.setItem(slot, itemStack);
    }

    public void updateItemLore(int slot, String lore) {
        ItemStack item = inventory.getItem(slot);
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            List<String> loreLines = new ArrayList<>();
            loreLines.add(lore);
            meta.setLore(loreLines);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
        }
    }

    public void updateItemDisplayName(int slot, String displayName) {
        ItemStack item = inventory.getItem(slot);
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(displayName);
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
        }
    }
}