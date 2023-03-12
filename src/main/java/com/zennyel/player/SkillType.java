package com.zennyel.player;

public enum SkillType {
    FAST_ATTACK("fast_attack"), HEAVY_ATTACK("heavy_attack"), LIGHT_ATTACK("light_attack");
    private String name;
    SkillType(String name){
        this.name = name;
    }

}
