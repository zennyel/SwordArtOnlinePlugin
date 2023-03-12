package com.zennyel.player;

import com.zennyel.SAO;
import com.zennyel.events.StatsLevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Character {

    private double level;
    private int agility;
    private int dexterity;
    private int strength;
    private int health;
    private int actualHealth;
    private List<Skill> skills;

    private int points;

    public Character(double level, int agility, int dexterity, int strength, int health, List<Skill> skills) {
        this.level = level;
        this.agility = agility;
        this.dexterity = dexterity;
        this.strength = strength;
        this.health = health;
        this.skills = skills;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void levelUp(){
        setLevel(getLevel() + 1);
        setPoints(getPoints() + 1);
    }
    public void removePoints(int value){
        if(getPoints() - value < 0){
            System.out.println("§4AÇÃO INVÁLIDA, O JOGADOR NÃO PODE FICAR COM PONTOS NEGATIVOS");
            return;
        }
        setPoints(getPoints() - value);
    }
    public void removeLevels(int value){
        if(getLevel() - value < 0){
            System.out.println("§4AÇÃO INVALIDA, O JOGADOR NÃO PODE FICAR COM LEVELS NEGATIVOS");
        }
        setLevel(getLevel() - value);
    }

    public void upStrength(Player player){
        Character character = SAO.getPlugin(SAO.class).getPlayer(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new StatsLevelUpEvent(character, player));
        setStrength(getStrength() + 1);
    }

    public void upAgility(Player player){
        Character character = SAO.getPlugin(SAO.class).getPlayer(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new StatsLevelUpEvent(character, player));
        setAgility(getAgility() + 1);
    }

    public void upDexterity(Player player){
        Character character = SAO.getPlugin(SAO.class).getPlayer(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new StatsLevelUpEvent(character, player));
        setDexterity(getDexterity() + 1);
    }

    public void upHealth(Player player){
        Character character = SAO.getPlugin(SAO.class).getPlayer(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new StatsLevelUpEvent(character, player));
        setHealth(getHealth() + 1);
    }

    public int getActualHealth() {
        return actualHealth;
    }

    public void setActualHealth(int actualHealth) {
        this.actualHealth = actualHealth;
    }
}
