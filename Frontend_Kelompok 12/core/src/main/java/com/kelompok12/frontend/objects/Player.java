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

    private float shootCooldown = 0.25f; // jeda antar tembakannya dalam detik, 0.25f = 4 peluru/detik
    private float shootTimer = 0f;

    public Player(float x, float y) {
        super(x, y, 32f, 32f, 200f, Color.BLUE);
    }

    @Override
    public void update(float delta) {
        if (shootTimer > 0f) {
            shootTimer -= delta;
        }
    }

    public void move(float dx, float dy) {
        if (dx != 0f || dy != 0f) {
            lastX = Math.signum(dx);    //biar di shoot nanti lastx nya gak 0, jadi gak 0 x 400f
            lastY = Math.signum(dy);
        }

        this.x += dx * this.speed;
        this.y += dy * this.speed;
    }

    //soalnya tiap pencet nembak harus tau posisi target tembakan biar bisa gerak kesana
    public void shoot(ProjectileManager projectileManager, float targetX, float targetY) {
        if (projectileManager == null || shootTimer > 0f) { //biar tembakannya di jeda
            return;
        }

        float centerX = this.x + (this.width / 2f);
        float centerY = this.y + (this.height / 2f);

        float dirX = targetX - centerX;
        float dirY = targetY - centerY;
        float length = (float) Math.hypot(dirX, dirY);

        if (length > 0f) {
            dirX /= length;
            dirY /= length;
        } else {
            dirX = lastX;
            dirY = lastY;
        }

        float bulletSpeed = 400f;
        float vx = dirX * bulletSpeed;
        float vy = dirY * bulletSpeed;

        float spawnX = centerX - 48f;
        float spawnY = centerY - 24f;

        projectileManager.spawnPlayerProjectile(spawnX, spawnY, vx, vy, this.damage);
        shootTimer = shootCooldown;
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
        System.out.println("EXP sekarang: " + this.exp);
    }

    public float getShootCooldown() {
        return shootCooldown;
    }

    public void setShootCooldown(float shootCooldown) {
        this.shootCooldown = Math.max(0.05f, shootCooldown); // batas paling cepetnya 0.05 detik
    }

}
