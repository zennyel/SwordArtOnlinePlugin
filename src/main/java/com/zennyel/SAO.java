package com.zennyel;

import com.zennyel.commands.AddLevel;
import com.zennyel.commands.Menu;
import com.zennyel.database.CharactersLoad;
import com.zennyel.database.MySQL;
import com.zennyel.listeners.*;
import com.zennyel.listeners.player.*;
import com.zennyel.player.*;
import com.zennyel.player.Character;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.*;

public final class SAO extends JavaPlugin {

    // Objeto de conexão com o banco de dados
    private MySQL sql;
    private final PlayerAPI playerAPI = new PlayerAPI(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Carrega os dados do jogo
        loadData();
        // Registra os eventos do jogo
        registerEvents();
        // Registra os comandos do jogo
        registerCommands();
        // Configura as tarefas em segundo plano
        new PlayerSync(this);
    }

    @Override
    public void onDisable() {
        // Salva os dados dos jogadores no banco de dados antes de desligar o plugin
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID id = p.getUniqueId();
            Character player = getPlayerAPI().getPlayer(id);
            sql.insertPlayer(player, id);
        }

        // Desconecta do banco de dados
        sql.disconnect();
    }

    // Registra todos os eventos do jogo
    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerLevelUp(), this);
        pm.registerEvents(new PlayerMove(), this);
        pm.registerEvents(new EntityDamage(), this);
        pm.registerEvents(new StatsLevelUp(), this);
        pm.registerEvents(new PlayerRegen(), this);
    }

    // Registra todos os comandos do jogo
    public void registerCommands() {
        getCommand("menu").setExecutor(new Menu());
        getCommand("addlevel").setExecutor(new AddLevel());
    }

    // Obtém objeto de conexão com o banco de dados
    public MySQL getSql() {
        return sql;
    }

    //método que cria uma conexão com o banco de dados, carrega o character do jogador.
    public void loadData() {
        sql = new MySQL(getConfig());
        sql.connect();
        sql.createTable();
        for (Player p : Bukkit.getOnlinePlayers()) {
            getPlayerAPI().loadCharacter(p);
        }
    }

    public PlayerAPI getPlayerAPI() {
        return playerAPI;
    }
}
