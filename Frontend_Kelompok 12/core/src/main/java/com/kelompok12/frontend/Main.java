package com.kelompok12.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Enemy;
import com.kelompok12.frontend.objects.enemies.Buzzer;
import com.kelompok12.frontend.objects.enemies.Satgas;
import com.kelompok12.frontend.objects.enemies.Boss;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.systems.CollisionReferee;
import com.kelompok12.frontend.systems.ProjectileManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private CollisionReferee collisionReferee;
    private ProjectileManager projectileManager;
    private List<GameObject> entities;
    private Player player;
    private List<Enemy> enemies;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        collisionReferee = new CollisionReferee();
        projectileManager = new ProjectileManager();

        entities = new ArrayList<>();
        player = new Player(100f, 240f);
        entities.add(player);

        enemies = new ArrayList<>();
        enemies.add(new Buzzer("Rakyat Biasa Terbius", 45, 420f, 300f));
        enemies.add(new Satgas("Satgas MBG", 75, 500f, 380f));
        enemies.add(new Boss("Presiden Tiran", 150, 560f, 200f));

        entities.addAll(enemies);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // Tembak menggunakan tombol SPACE atau klik mouse
        if (player.isActive() && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())) {
            Projectile bullet = player.shoot();
            if (bullet != null) {
                entities.add(bullet);
            }
        }

        // Update semua entity aktif
        for (int i = 0; i < entities.size(); i++) {
            GameObject obj = entities.get(i);
            if (obj.isActive()) {
                obj.update(delta);
            }
        }

        // Jalankan pengecekan collision
        collisionReferee.resolveCollisions(entities);

        // Bersihkan entity yang sudah mati (active == false) atau peluru keluar layar
        Iterator<GameObject> iterator = entities.iterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.next();
            if (!obj.isActive() || isOutOfBounds(obj)) {
                if (obj instanceof Enemy) {
                    enemies.remove(obj);
                }
                iterator.remove();
            }
        }

        ScreenUtils.clear(0.12f, 0.13f, 0.16f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            if (obj.isActive()) {
                obj.render(shapeRenderer);
            }
        }
        shapeRenderer.end();
    }

    private boolean isOutOfBounds(GameObject obj) {
        if (!(obj instanceof Projectile)) {
            return false;
        }
        float margin = 100f;
        return obj.getX() < -margin
            || obj.getX() > Gdx.graphics.getWidth() + margin
            || obj.getY() < -margin
            || obj.getY() > Gdx.graphics.getHeight() + margin;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
