package com.zennyel.player;

import java.util.List;

public class Player {

    private double level;
    private int agility;
    private int dexterity;
    private int strength;
    private int health;
    private List<Skill> skills;

    private int points;

    public Player(double level, int agility, int dexterity, int strength, int health, List<Skill> skills) {
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

    public void upStrength(){
        setStrength(getStrength() + 1);
    }

    public void upAgility(){
        setAgility(getAgility() + 1);
    }

    public void upDexterity(){
        setDexterity(getDexterity() + 1);
    }

    public void upHealth(){
        setHealth(getHealth() + 1);
    }



}
