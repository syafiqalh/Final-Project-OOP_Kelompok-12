package com.kelompok12.frontend.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.projectiles.ProjectileType;

import java.util.*;

public class ProjectileManager {
    // Object Pool Pattern
    private Queue<Projectile> playerProjectilePool;
    private Queue<Projectile> enemyProjectilePool;
    private List<GameObject> entities;

    private List<Projectile> activePlayerProjectiles;
    private List<Projectile> activeEnemyProjectiles;

    public ProjectileManager() {
        playerProjectilePool = new LinkedList<>();
        enemyProjectilePool = new LinkedList<>();
        activePlayerProjectiles = new ArrayList<>();
        activeEnemyProjectiles = new ArrayList<>();
    }

    public Projectile spawnPlayerProjectile(float x, float y, float vx, float vy, int damage) {
        Projectile projectile;
        if (!playerProjectilePool.isEmpty()) {
            projectile = playerProjectilePool.poll();
            projectile.init(x, y, vx, vy, ProjectileType.ANTIDOTE, damage);
        } else {
            projectile = EntityFactory.createPlayerProjectile(x, y, vx, vy, damage);
            projectile.init(x, y, vx, vy, ProjectileType.ANTIDOTE, damage);
        }
        activePlayerProjectiles.add(projectile);
        return projectile;
    }

    public Projectile spawnEnemyProjectile(float x, float y, float vx, float vy, int damage) {
        Projectile projectile;
        if (!enemyProjectilePool.isEmpty()) {
            projectile = enemyProjectilePool.poll();
            projectile.init(x, y, vx, vy, ProjectileType.BULLET, damage);
        } else {
            projectile = EntityFactory.createEnemyProjectile(x, y, vx, vy, damage);
            projectile.init(x, y, vx, vy, ProjectileType.BULLET, damage);
        }
        activeEnemyProjectiles.add(projectile);
        return projectile;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        Iterator<Projectile> playerIt = activePlayerProjectiles.iterator();
        while (playerIt.hasNext()) {
            Projectile projectile = playerIt.next();
            projectile.update(delta);
            if (projectile.isOffScreen(screenWidth, screenHeight) || !projectile.isActive()) {
                projectile.destroy();
                playerIt.remove();
                playerProjectilePool.offer(projectile);
            }
        }

        Iterator<Projectile> enemyIt = activeEnemyProjectiles.iterator();
        while (enemyIt.hasNext()) {
            Projectile projectile = enemyIt.next();
            projectile.update(delta);
            if (projectile.isOffScreen(screenWidth, screenHeight) || !projectile.isActive()) {
                projectile.destroy();
                enemyIt.remove();
                enemyProjectilePool.offer(projectile);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (Projectile p : activePlayerProjectiles) {
            if (p.isActive()) p.render(shapeRenderer);
        }
        for (Projectile p : activeEnemyProjectiles) {
            if (p.isActive()) p.render(shapeRenderer);
        }
    }

    public void render(SpriteBatch batch) {
        for (Projectile p : activePlayerProjectiles) {
            if (p.isActive()) p.render(batch);
        }
        for (Projectile p : activeEnemyProjectiles) {
            if (p.isActive()) p.render(batch);
        }
    }

    public void clearPlayerProjectiles() {
        Iterator<Projectile> it = activePlayerProjectiles.iterator();
        while (it.hasNext()) {
            Projectile projectile = it.next();
            projectile.destroy();
            it.remove();
            playerProjectilePool.offer(projectile);
        }
    }

    public void clearEnemyProjectiles() {
        Iterator<Projectile> it = activeEnemyProjectiles.iterator();
        while (it.hasNext()) {
            Projectile projectile = it.next();
            projectile.destroy();
            it.remove();
            enemyProjectilePool.offer(projectile);
        }
    }

    public List<Projectile> getActivePlayerProjectiles() { return activePlayerProjectiles; }
    public List<Projectile> getActiveEnemyProjectiles() { return activeEnemyProjectiles; }
    public int getPlayerPoolSize() { return playerProjectilePool.size(); }
    public int getEnemyPoolSize() { return enemyProjectilePool.size(); }
    public List<GameObject> getEntities() { return entities; }
    public void setEntities(List<GameObject> entities) { this.entities = entities; }
}
