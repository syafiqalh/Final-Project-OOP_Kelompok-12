package com.kelompok12.frontend.objects;
import com.badlogic.gdx.graphics.Color;

import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.projectiles.ProjectileType;
import com.kelompok12.frontend.systems.ProjectileManager;

public class Player extends GameObject{

    private int hp = 100;
    private int maxHp = hp;
    private int damage = 15;
    private int exp = 0;

    private float lastX = 1f;
    private float lastY = 0f;

    public Player(float x, float y) {
        super(x, y, 62f, 89f, 200f, Color.BLUE);
    }

    public void move(float dx, float dy) {
        if (dx != 0f || dy != 0f) {
            lastX = Math.signum(dx);    //biar di shoot nanti lastx nya gak 0, jadi gak 0 x 400f
            lastY = Math.signum(dy);
        }

        this.x += dx * this.speed;
        this.y += dy * this.speed;
    }

    public void shoot(ProjectileManager projectileManager) {

        if (projectileManager == null) {    //biar kalo lupa bikin instansiasi baru programnya gak crash
            return;
        }

        float spawnX = this.x + (this.width / 2f) - 8f;
        float spawnY = this.y + (this.height / 2f) - 8f;

        float bulletSpeed = 400f;
        float vx = lastX * bulletSpeed;
        float vy = lastY * bulletSpeed;

        projectileManager.spawnPlayerProjectile(spawnX, spawnY, vx, vy, this.damage);
    }

    public void takeDamage(int damage) {
        if (damage > 0) {
            this.hp -= damage;
            if (this.hp <= 0) {
                this.hp = 0;
                destroy();
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
