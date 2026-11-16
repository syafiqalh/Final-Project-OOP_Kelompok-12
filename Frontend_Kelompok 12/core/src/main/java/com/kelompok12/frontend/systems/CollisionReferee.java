package com.kelompok12.frontend.systems;

import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.objects.enemies.Enemy;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.projectiles.ProjectileType;

import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(List<GameObject> entities) {
        for (int i = 0; i < entities.size(); i++) {
            GameObject a = entities.get(i);
            if (a == null || !a.isActive()) {
                continue;
            }

            for (int j = i + 1; j < entities.size(); j++) {
                GameObject b = entities.get(j);
                if (b == null || !b.isActive()) {
                    continue;
                }

                checkAndResolveCollision(a, b);

                if (!a.isActive()) {
                    break;
                }
            }
        }
    }

    /**
     * Memeriksa apakah dua GameObject saling bertabrakan dan mengeksekusi logika akibat tabrakan tersebut.
     *
     * @param a objek pertama
     * @param b objek kedua
     * @return true jika terjadi tabrakan dan berhasil ditangani
     */
    public boolean checkAndResolveCollision(GameObject a, GameObject b) {

        Projectile projectile = null;
        Enemy enemy = null;

        if (a instanceof Projectile && b instanceof Enemy) {
            projectile = (Projectile) a;
            enemy = (Enemy) b;
        }

        if (projectile != null && enemy != null) {
            return handleProjectileEnemyCollision(projectile, enemy);
        }

        return false;
    }

    public boolean handleProjectileEnemyCollision(Projectile projectile, Enemy enemy) {
        if (projectile == null || enemy == null) {
            return false;
        }

        if (!projectile.isActive() || !enemy.isActive()) {
            return false;
        }

        if (projectile.getProjectileType() != ProjectileType.ANTIDOTE) {
            return false;
        }

        if (projectile.getHitbox().overlaps(enemy.getHitbox())) {
            int damage = projectile.getDamage();
            projectile.destroy();
            enemy.takeDamage(damage);

            projectile.onCollision(enemy);
            enemy.onCollision(projectile);
            return true;
        }

        return false;
    }
}
