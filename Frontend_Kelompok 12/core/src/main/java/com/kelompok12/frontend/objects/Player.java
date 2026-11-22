package com.kelompok12.frontend.objects;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.projectiles.ProjectileType;

public class Player extends GameObject{

    private int hp = 100;
    private int maxHp = hp;
    private int damage = 15;
    private int exp = 0;

    private float lastX = 1f;
    private float lastY = 0f;

    public Player(float x, float y) {
        super(x, y, 32f, 32f, 200f, Color.BLUE);
    }

    @Override
    public void update(float delta) {
        float x = 0f;
        float y = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 1f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 1f;
        }

        if (x != 0f || y != 0f) {
            lastX = x;
            lastY = y;
        }

        this.x += x * this.speed * delta;
        this.y += y * this.speed * delta;
    }

    public Projectile shoot() {
        float spawnX = this.x + (this.width / 2f) - 8f;
        float spawnY = this.y + (this.height / 2f) - 8f;

        float bulletSpeed = 400f;
        float vx = lastX * bulletSpeed;
        float vy = lastY * bulletSpeed;

        return new Projectile(spawnX, spawnY, vx, vy, ProjectileType.ANTIDOTE, this.damage);
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
