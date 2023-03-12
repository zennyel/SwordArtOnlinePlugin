package com.zennyel;

import com.zennyel.commands.AddLevel;
import com.zennyel.commands.Menu;
import com.zennyel.database.CharactersLoad;
import com.zennyel.database.MySQL;
import com.zennyel.listeners.*;
import com.zennyel.listeners.player.*;
import com.zennyel.player.Character;
import com.zennyel.player.Skill;
import com.zennyel.player.SkillType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.*;

public final class SAO extends JavaPlugin {

    // HashMap para armazenar informações do personagem do jogador
    private final HashMap<UUID, Character> playerHashMap = new HashMap<>();

    // Objeto de conexão com o banco de dados
    private MySQL sql;

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
        runnableTasks();
    }

    @Override
    public void onDisable() {
        // Salva os dados dos jogadores no banco de dados antes de desligar o plugin
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID id = p.getUniqueId();
            Character player = getPlayer(id);
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

    // Carrega os dados do jogador
    public void loadCharacter(Player p) {
        Character player = sql.getPlayer(p.getUniqueId());
        playerHashMap.put(p.getUniqueId(), player);
    }

    // Obtém informações do personagem do jogador
    public Character getPlayer(UUID id) {
        return playerHashMap.get(id);
    }

    // Obtém objeto de conexão com o banco de dados
    public MySQL getSql() {
        return sql;
    }

    // Cria um novo personagem para o jogador
    public Character createCharacter(Player player) {
        List<Skill> skills = Arrays.asList(
                new Skill(0, SkillType.LIGHT_ATTACK),
                new Skill(0, SkillType.FAST_ATTACK),
                new Skill(0, SkillType.HEAVY_ATTACK)
        );
        return new Character(0, 0, 0, 0, 0, skills);
    }

    //cria tarefas síncronas, uma mostra a vida do jogador na tela e atualiza a cada segundo, a outra regenera 1% a cada 10
    public void runnableTasks() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            new CharactersLoad(sql, p);
            Character chara = getPlayer(p.getUniqueId());
            chara.setActualHealth(chara.getHealth());
            int healthBar = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                int health = (int) p.getHealth() + getPlayer(p.getUniqueId()).getActualHealth();
                int maxHealth = chara.getHealth() + 20;
                if(health < maxHealth){
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lVIDA: " + health + "/" + maxHealth));
                    return;
                }
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lVIDA: " + health));
            }, 0L, 20L);

            int healthRegen = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                Character character = getPlayer(p.getUniqueId());
                int actualHealth = character.getActualHealth();
                if (actualHealth > character.getHealth()) {
                    character.setActualHealth((int) (actualHealth + actualHealth * 0.09 / 50));
                }
            }, 0L, 40L);
        }
    }

    //método que cria uma conexão com o banco de dados, carrega o character do jogador.
    public void loadData() {
        sql = new MySQL(getConfig());
        sql.connect();
        sql.createTable();
        for (Player p : Bukkit.getOnlinePlayers()) {
            loadCharacter(p);
        }
    }
}
