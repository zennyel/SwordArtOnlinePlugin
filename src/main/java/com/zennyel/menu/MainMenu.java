package com.zennyel.menu;

import com.zennyel.player.Character;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private final Inventory inventory;

    public MainMenu(Inventory inventory, Character character) {
        this.inventory = inventory;
        addItems(character);
    }

    public void addItems(Character character){
        addCenteredItem(new ItemStack(Material.PLAYER_HEAD), "§6Menu de Status", "§7Clique para acessar", 11);
        addCenteredItem(new ItemStack(Material.NETHERITE_SWORD), "§6Menu de Skills", "§7Clique para acessar", 13);
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
