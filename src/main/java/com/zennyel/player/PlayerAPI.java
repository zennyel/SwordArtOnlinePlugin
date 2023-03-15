package com.zennyel.player;

import com.zennyel.SAO;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerAPI {

    private SAO instance;

    public PlayerAPI(SAO instance) {
        this.instance = instance;
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
    // Carrega os dados do jogador
    public void loadCharacter(Player p) {
        Character player = instance.getSql().getPlayer(p.getUniqueId());
        playerHashMap.put(p.getUniqueId(), player);
    }

    // Obtém informações do personagem do jogador
    public Character getPlayer(UUID id) {
        return playerHashMap.get(id);
    }
    // HashMap para armazenar informações do personagem do jogador
    private final HashMap<UUID, Character> playerHashMap = new HashMap<>();

    public SAO getInstance() {
        return instance;
    }
}
