package com.kelompok12.frontend.systems;

import com.kelompok12.frontend.objects.EXP;
import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Enemy;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.projectiles.ProjectileType;

import java.util.List;

public class CollisionReferee {
    private List<GameObject> entities;

    public void resolveCollisions(List<GameObject> entities) {
        this.entities = entities;
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

    public boolean checkAndResolveCollision(GameObject a, GameObject b) {

        if (!a.getHitbox().overlaps(b.getHitbox())) { return false; }

        // Projectile-enemy
        if (a instanceof Projectile projectile && b instanceof Enemy enemy) {
            return handleProjectileEnemyCollision(projectile, enemy);
        }
        else if (a instanceof Enemy enemy && b instanceof Projectile projectile) {
            return handleProjectileEnemyCollision(projectile, enemy);
        }

        //Player-enemy
        if (a instanceof Player player && b instanceof Enemy enemy) {
            return handlePlayerEnemyCollision(player, enemy);
        }
        else if (a instanceof Enemy enemy && b instanceof Player player) {
            return handlePlayerEnemyCollision(player, enemy);
        }


        // Player-exp
        if (a instanceof Player player && b instanceof EXP exp) {
            return handlePlayerExpCollision(player, exp);
        }
        else if (a instanceof EXP exp && b instanceof Player player) {
            return handlePlayerExpCollision(player, exp);
        }

        return false;
    }


    public boolean handleProjectileEnemyCollision(Projectile projectile, Enemy enemy) {

        if (projectile.getProjectileType() != ProjectileType.ANTIDOTE) {
            return false;
        }

        if (projectile.getHitbox().overlaps(enemy.getHitbox())) {
            int damage = projectile.getDamage();
            projectile.destroy();
            enemy.takeDamage(damage);
            if (!enemy.isAlive()) entities.add(EntityFactory.createExp(enemy.getX()+15, enemy.getY()+15, 10));
            System.out.println("enemy ditembak");

            projectile.onCollision(enemy);
            enemy.onCollision(projectile);
            return true;
        }

        return false;
    }

    public boolean handlePlayerEnemyCollision(Player player, Enemy enemy) {

        if (player.getHitbox().overlaps(enemy.getHitbox())) {
            player.takeDamage(10);
            return true;
        }

        return false;
    }


    public boolean handlePlayerExpCollision(Player player, EXP exp) {

        if (player.getHitbox().overlaps(exp.getHitbox())) {
            player.setExp(player.getExp() + exp.getAmount());
            exp.destroy();
            return true;
        }
        return false;
    }

}
