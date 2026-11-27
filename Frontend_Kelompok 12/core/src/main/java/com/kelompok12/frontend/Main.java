package com.kelompok12.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.kelompok12.frontend.commands.Command;
import com.kelompok12.frontend.commands.InputHandler;
import com.kelompok12.frontend.commands.ShootCommand;
import com.kelompok12.frontend.MainMenu.MainMenuScreen;
import com.kelompok12.frontend.objects.EXP;
import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Boss;
import com.kelompok12.frontend.objects.enemies.Buzzer;
import com.kelompok12.frontend.objects.enemies.Enemy;
import com.kelompok12.frontend.objects.enemies.Satgas;
import com.kelompok12.frontend.projectiles.Projectile;
import com.kelompok12.frontend.systems.AssetManager;
import com.kelompok12.frontend.systems.CollisionReferee;
import com.kelompok12.frontend.systems.EntityFactory;
import com.kelompok12.frontend.systems.ProjectileManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private CollisionReferee collisionReferee;
    private ProjectileManager projectileManager;
    private InputHandler inputHandler;
    private MainMenuScreen mainMenuScreen;

    private List<GameObject> entities;
    private Player player;
    private List<Enemy> enemies;
    private boolean inMenu = false;

    private SpriteBatch batch;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        AssetManager.getInstance().init();
        collisionReferee = new CollisionReferee();
        projectileManager = new ProjectileManager();
        inputHandler = new InputHandler();

        entities = new ArrayList<>();
        projectileManager.setEntities(entities);

        player = EntityFactory.createPlayer(100f, 240f);
        entities.add(player);

        enemies = new ArrayList<>();
        enemies.add(EntityFactory.createBuzzer("Rakyat Biasa Terbius", 45, 420f, 300f, player));
        enemies.add(EntityFactory.createSatgas("Satgas MBG", 75, 500f, 380f));
        enemies.add(EntityFactory.createBoss("Presiden Tiran", 150, 560f, 200f));
        entities.addAll(enemies);

        entities.add(EntityFactory.createExp(280f, 240f, 15));

        if (inMenu) {
            mainMenuScreen = new MainMenuScreen(this);
        } else {
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            inMenu = !inMenu;
            if (inMenu) {
                mainMenuScreen = new MainMenuScreen(this);
            } else {
                if (mainMenuScreen != null) {
                    mainMenuScreen.dispose();
                    mainMenuScreen = null;
                }
                Gdx.input.setInputProcessor(null);
            }
        }

        if (inMenu && mainMenuScreen != null) {
            mainMenuScreen.render(delta);
            return;
        }

        List<Command> commands = inputHandler.handleInput(delta);
        for (Command command : commands) {
            command.execute(player, projectileManager);
        }

        if (player.isActive() && (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())) {
            new ShootCommand().execute(player, projectileManager);
        }

        projectileManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (Projectile p : projectileManager.getActivePlayerProjectiles()) {
            if (!entities.contains(p)) {
                entities.add(p);
            }
        }
        for (Projectile p : projectileManager.getActiveEnemyProjectiles()) {
            if (!entities.contains(p)) {
                entities.add(p);
            }
        }

        for (int i = 0; i < entities.size(); i++) {
            GameObject obj = entities.get(i);
            if (obj.isActive() && !(obj instanceof Projectile)) {
                obj.update(delta);
            }
        }

        collisionReferee.resolveCollisions(entities);

        Iterator<GameObject> iterator = entities.iterator();
        while (iterator.hasNext()) {
            GameObject obj = iterator.next();
            if (!obj.isActive() || isOutOfBounds(obj)) {
                if (obj instanceof Enemy) {
                    enemies.remove(obj);
                }
                if (obj instanceof Projectile) {
                    obj.destroy();
                }
                iterator.remove();
            }
        }

        ScreenUtils.clear(0.12f, 0.13f, 0.16f, 1f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (GameObject obj : entities) {
            if (obj.isActive() && !(obj instanceof Projectile) && !(obj instanceof Buzzer) && !(obj instanceof Satgas)) {
                obj.render(shapeRenderer);
            }
        }
        projectileManager.render(shapeRenderer);
        shapeRenderer.end();
        batch.begin();
        for (GameObject obj : entities) {
            if (obj.isActive() && (obj instanceof Buzzer || obj instanceof Satgas)) {
                obj.render(batch);
            }
        }
        batch.end();
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
        if (batch != null) {
            batch.dispose();
        }
        AssetManager.getInstance().dispose();
        if (mainMenuScreen != null) {
            mainMenuScreen.dispose();
        }
    }
}
