package com.zennyel.database;

import com.zennyel.SAO;
import com.zennyel.player.Character;
import com.zennyel.player.PlayerAPI;
import org.bukkit.entity.Player;

public class CharactersLoad {

    private MySQL sql;
    private Player player;
    private Character character;
    private SAO instance;
    private PlayerAPI playerAPI;


    public CharactersLoad(MySQL sql, Player player){
        this.sql = sql;
        this.player = player;
        this.instance = SAO.getPlugin(SAO.class);
        this.playerAPI = getInstance().getPlayerAPI();
        loadCharacters();
    }

    public boolean isRegistered(){
        if(sql.getPlayer(player.getUniqueId()) != null){
            return true;
        }
        return false;
    }

    public void loadCharacters(){
        if(isRegistered()){
            playerAPI.loadCharacter(player);
            return;
        }
        character = playerAPI.createCharacter(player);
        getSql().insertPlayer(character, player.getUniqueId());
        playerAPI.loadCharacter(player);
    }

    public SAO getInstance() {
        return instance;
    }

    public void setInstance(SAO instance) {
        this.instance = instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MySQL getSql() {
        return sql;
    }

    public void setSql(MySQL sql) {
        this.sql = sql;
    }


}
