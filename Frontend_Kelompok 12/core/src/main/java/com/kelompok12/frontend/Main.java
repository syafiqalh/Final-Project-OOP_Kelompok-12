package com.kelompok12.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Enemy;
import com.kelompok12.frontend.objects.enemies.Buzzer;
import com.kelompok12.frontend.objects.enemies.Satgas;
import com.kelompok12.frontend.objects.enemies.Boss;
import com.kelompok12.frontend.systems.CollisionReferee;
import com.kelompok12.frontend.systems.ProjectileManager;

import java.util.ArrayList;
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
        player = new Player(100f, 280f);
        entities.add(player);

        enemies = new ArrayList<>();
        enemies.add(new Buzzer("Rakyat Biasa Terbius", 50, 450f, 300f));
        enemies.add(new Satgas("Satgas MBG", 80, 580f, 400f));
        enemies.add(new Boss("Presiden Tiran", 500, 680f, 220f));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        for (GameObject obj : entities) {
            obj.update(delta);
        }

        collisionReferee.resolveCollisions(entities);

        ScreenUtils.clear(0.12f, 0.13f, 0.16f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            obj.render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
