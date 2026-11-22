
package com.kelompok12.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.systems.EntityFactory;

public class Enemy extends GameObject {

    protected String name;
    protected int hp;
    protected int maxHP;

    public Enemy(String name, int hp, float x, float y) {
        super(x, y, 32f, 32f, 100f, Color.RED);

        this.name = name;
        this.hp = Math.max(0, hp);
        this.maxHP = this.hp;

        if (this.hp == 0) {
            destroy();
        }
    }

    public void takeDamage(int damage) {
        if (damage <= 0 || !isActive()) {
            return;
        }

        this.hp -= damage;

        if (this.hp <= 0) {
            this.hp = 0;
            destroy();
        }
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public boolean isAlive() {
        return hp > 0 && isActive();
    }
}
