package com.kelompok12.frontend.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.kelompok12.frontend.objects.GameObject;

public class Projectile extends GameObject {
    private ProjectileType projectileType;
    private int damage;
    private float vx, vy;
    private float angle;

    public Projectile(float x, float y, ProjectileType projectileType, int damage) {
        super(x, y, 16f, 16f, 100f, Color.YELLOW);
        this.projectileType = projectileType;
        this.damage = damage;
        this.vx = this.speed;
        this.vy = 0f;
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
    }

    public Projectile(float x, float y, float vx, float vy, ProjectileType projectileType, int damage) {
        super(x, y, 16f, 16f, 100f, Color.YELLOW);
        this.projectileType = projectileType;
        this.damage = damage;
        this.vx = vx;
        this.vy = vy;
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.x += vx * delta;
        this.y += vy * delta;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
    }

    public void setDirection(float angleDegrees) {
        this.angle = angleDegrees;
        float rad = (float) Math.toRadians(angleDegrees);
        this.vx = (float) Math.cos(rad) * speed;
        this.vy = (float) Math.sin(rad) * speed;
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
