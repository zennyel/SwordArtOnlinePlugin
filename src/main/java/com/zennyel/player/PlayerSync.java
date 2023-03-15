package com.zennyel.player;

import com.zennyel.SAO;
import com.zennyel.database.CharactersLoad;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerSync {

    private PlayerAPI playerAPI;
    private SAO instance;

    public PlayerSync(SAO instance) {
        this.playerAPI = getInstance().getPlayerAPI();
        this.instance = instance;
    }

    //cria tarefas síncronas, uma mostra a vida do jogador na tela e atualiza a cada segundo, a outra regenera 1% a cada 10
    public void runnableTasks() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            new CharactersLoad(getInstance().getSql(), p);
            Character chara = playerAPI.getPlayer(p.getUniqueId());
            chara.setActualHealth(chara.getHealth());
            int healthBar = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
                int health = (int) p.getHealth() + playerAPI.getPlayer(p.getUniqueId()).getActualHealth();
                int maxHealth = chara.getHealth() + 20;
                if(health < maxHealth){
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lVIDA: " + health + "/" + maxHealth));
                    return;
                }
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§c§lVIDA: " + health));
            }, 0L, 20L);

            int healthRegen = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, () -> {
                Character character = playerAPI.getPlayer(p.getUniqueId());
                int actualHealth = character.getActualHealth();
                if (actualHealth > character.getHealth()) {
                    character.setActualHealth((int) (actualHealth + actualHealth * 0.09 / 50));
                }
            }, 0L, 40L);
        }
    }

    public SAO getInstance() {
        return instance;
    }
}
