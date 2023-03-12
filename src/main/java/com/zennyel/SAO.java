package com.zennyel;

import com.zennyel.commands.AddLevel;
import com.zennyel.commands.Menu;
import com.zennyel.database.MySQL;
import com.zennyel.listeners.*;
import com.zennyel.player.Character;
import com.zennyel.player.Skill;
import com.zennyel.player.SkillType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SAO extends JavaPlugin {

    private HashMap<UUID, Character> playerHashMap;
    private MySQL sql;
    private Connection connection;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        sql = new MySQL(getConfig());
        sql.connect();
        sql.createTable();
        connection = sql.getConnection();
        playerHashMap = new HashMap<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(SAO.getPlugin(SAO.class).getSql().getPlayer(p.getUniqueId()) == null || getSql().getPlayer(p.getUniqueId()) == null){
                SAO.getPlugin(SAO.class).createCharacter(p);
            }
            SAO.getPlugin(SAO.class).loadCharacter(p);
            Character chara = SAO.getPlugin(SAO.class).getPlayer(p.getUniqueId());
            chara.setActualHealth(chara.getHealth());
            int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(SAO.class), new Runnable() {
                @Override
                public void run() {
                    if (p != null) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lVIDA: " + getPlayer(p.getUniqueId()).getActualHealth()));
                    }
                }
            }, 0L, 20L);
        }

    }

    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()){
            UUID id = p.getUniqueId();
            Character player = SAO.getPlugin(SAO.class).getPlayer(id);
            SAO.getPlugin(SAO.class).getSql().insertPlayer(player, id);
        }
        getSql().disconnect();
    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerLevelUp(), this);
        pm.registerEvents(new PlayerMove(), this);
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new StatsLevelUp(), this);
    }
    public void registerCommands(){
        getCommand("menu").setExecutor(new Menu());
        getCommand("addlevel").setExecutor(new AddLevel());
    }

    public void loadCharacter(Player p){
        Character player = getSql().getPlayer(p.getUniqueId());
        playerHashMap.put(p.getUniqueId(), player);
    }

    public HashMap<UUID, Character> getPlayerHashMap() {
        return playerHashMap;
    }

    public void setPlayerHashMap(HashMap<UUID, Character> playerHashMap) {
        this.playerHashMap = playerHashMap;
    }

    public Character getPlayer(UUID id){
        return playerHashMap.get(id);
    }

    public MySQL getSql() {
        return sql;
    }

    public Connection getConnection() {
        return connection;
    }

    public void createCharacter(Player player){
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(0, SkillType.LIGHT_ATTACK));
        skills.add(new Skill(0, SkillType.FAST_ATTACK));
        skills.add(new Skill(0, SkillType.HEAVY_ATTACK));
        Character p = new Character(0,0,0,0,0, skills);
        SAO.getPlugin(SAO.class).getSql().insertPlayer(p, player.getUniqueId());
    }

}
