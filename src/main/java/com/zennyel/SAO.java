package com.zennyel;

import com.zennyel.commands.AddLevel;
import com.zennyel.commands.Menu;
import com.zennyel.database.MySQL;
import com.zennyel.listeners.InventoryClick;
import com.zennyel.listeners.PlayerInteract;
import com.zennyel.listeners.PlayerJoin;
import com.zennyel.listeners.PlayerLevelUp;
import com.zennyel.player.Skill;
import com.zennyel.player.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SAO extends JavaPlugin {

    private HashMap<UUID, com.zennyel.player.Player> playerHashMap;
    private MySQL sql;
    private Connection connection;
    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        sql = new MySQL(getConfig());
        sql.connect();
        sql.createTable();
        connection = sql.getConnection();
        playerHashMap = new HashMap<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(SAO.getPlugin(SAO.class).getSql().getPlayer(p.getUniqueId()) == null){
                SAO.getPlugin(SAO.class).createCharacter(p);
            }
            SAO.getPlugin(SAO.class).loadCharacter(p);
        }
    }

    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()){
            UUID id = p.getUniqueId();
            com.zennyel.player.Player player = SAO.getPlugin(SAO.class).getPlayer(id);
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
    }
    public void registerCommands(){
        getCommand("menu").setExecutor(new Menu());
        getCommand("addpoints").setExecutor(new AddLevel());
        getCommand("addlevel").setExecutor(new AddLevel());
    }

    public void loadCharacter(Player p){
        com.zennyel.player.Player player = getSql().getPlayer(p.getUniqueId());
        playerHashMap.put(p.getUniqueId(), player);
    }

    public HashMap<UUID, com.zennyel.player.Player> getPlayerHashMap() {
        return playerHashMap;
    }

    public void setPlayerHashMap(HashMap<UUID, com.zennyel.player.Player> playerHashMap) {
        this.playerHashMap = playerHashMap;
    }

    public com.zennyel.player.Player getPlayer(UUID id){
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
        com.zennyel.player.Player p = new com.zennyel.player.Player(0,0,0,0,0, skills);
        SAO.getPlugin(SAO.class).getSql().insertPlayer(p, player.getUniqueId());
    }

}
