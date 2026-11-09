package com.kelompok12.frontend.enemies;

public class Enemy {
    protected String name;
    protected int hp;
    protected int maxHP;
    protected float x;
    protected float y;

    public Enemy(String name, int hp, float x, float y) {
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.x = x;
        this.y = y;
    }
}
