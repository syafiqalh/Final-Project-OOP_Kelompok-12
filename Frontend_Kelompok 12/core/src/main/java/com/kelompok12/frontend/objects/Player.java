package com.kelompok12.frontend.objects;

import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject{

    private int hp = 100;
    private int maxHp = hp;
    private int damage;

    public Player(float x, float y) {
        super(x, y, 32f, 32f, 200f, Color.BLUE);
    }

    public void takeDamage(int damage) {
        if (damage > 0) {
            this.hp -= damage;
            if (this.hp < 0) {
                this.hp = 0;
            }
        }
    }

    public boolean isAlive() {
        if(this.hp > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void shootProjectile() {}

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage= damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}
