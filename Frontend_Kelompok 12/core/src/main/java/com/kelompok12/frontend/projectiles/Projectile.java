package com.kelompok12.frontend.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.kelompok12.frontend.objects.GameObject;

public class Projectile extends GameObject {
    private ProjectileType projectileType;
    private int damage;
    private float vx, vy;
    private float angle;

    Projectile(float x, float y, ProjectileType projectileType, int damage){
        super(x, y, 16 , 16, 1000, Color.YELLOW);
        this.projectileType = projectileType;
        this.damage = damage;
        this.vx = 0f;
        this.vy = 0f;
        this.angle = 0f;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileType projectileType) {
        this.projectileType = projectileType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
