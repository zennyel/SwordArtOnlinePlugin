package com.zennyel.player;

public class Skill {

    private int damage;
    private String name;
    private SkillType type;

    private int level;
    private long lastUsed = 0;

    private boolean isActive;

    public Skill(int damage, SkillType type) {
        this.damage = damage;
        this.name = type.toString();
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public void activate() {
        if (System.currentTimeMillis() - lastUsed > 5000) { // 5 segundos de tempo de espera
            isActive = true;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
