package com.kelompok12.frontend.systems;

import com.kelompok12.frontend.objects.EXP;
import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Boss;
import com.kelompok12.frontend.objects.enemies.Buzzer;
import com.kelompok12.frontend.objects.enemies.Satgas;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.projectiles.ProjectileType;

public class EntityFactory {

    public static Player createPlayer(float x, float y) {
        return new Player(x, y);
    }

    public static Buzzer createBuzzer(float x, float y) {
        return new Buzzer("Rakyat Biasa Terbius", 45, x, y);
    }

    public static Buzzer createBuzzer(String name, int hp, float x, float y) {
        return new Buzzer(name, hp, x, y);
    }

    public static Satgas createSatgas(float x, float y) {
        return new Satgas("Satgas MBG", 75, x, y);
    }

    public static Satgas createSatgas(String name, int hp, float x, float y) {
        return new Satgas(name, hp, x, y);
    }

    public static Boss createBoss(float x, float y) {
        return new Boss("Presiden Tiran", 150, x, y);
    }

    public static Boss createBoss(String name, int hp, float x, float y) {
        return new Boss(name, hp, x, y);
    }

    public static Projectile createPlayerProjectile(float x, float y,float vx, float vy, int damage) {
        return new Projectile(x, y, vx, vy, ProjectileType.ANTIDOTE, damage);
    }

    public static Projectile createEnemyProjectile(float x, float y, float vx, float vy, int damage) {
        return new Projectile(x, y, vx, vy, ProjectileType.BULLET, damage);
    }

    public static Projectile createProjectile(float x, float y, float vx, float vy, ProjectileType type, int damage) {
        return new Projectile(x, y, vx, vy, type, damage);
    }

    public static EXP createExp(float x, float y, int amount) {
        return new EXP(x, y, amount);
    }
}
