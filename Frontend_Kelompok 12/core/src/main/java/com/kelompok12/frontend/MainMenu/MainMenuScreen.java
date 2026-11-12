package com.kelompok12.frontend.MainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kelompok12.frontend.Main;

public class MainMenuScreen implements Screen {
    Main game;
    Stage stage;
    Skin skin;
    Texture buttonTexture;
    Texture buttonHoverTexture;

    public MainMenuScreen(final Main game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        skin.add("default", new BitmapFont());

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.25f, 0.25f, 0.35f, 1f));
        pixmap.fill();
        buttonTexture = new Texture(pixmap);

        pixmap.setColor(new Color(0.35f, 0.45f, 0.65f, 1f));
        pixmap.fill();
        buttonHoverTexture = new Texture(pixmap);
        pixmap.dispose();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.up = new TextureRegionDrawable(buttonTexture);
        textButtonStyle.over = new TextureRegionDrawable(buttonHoverTexture); // Efek saat kursor di atas tombol
        textButtonStyle.down = new TextureRegionDrawable(buttonHoverTexture);
        skin.add("default", textButtonStyle);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Tombol 1: Play Game
        TextButton playButton = new TextButton("PLAY GAME", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Tombol Play diklik!");
            }
        });

        // Tombol 2: Top 5 Leaderboard
        TextButton leaderboardButton = new TextButton("TOP 5 LEADERBOARD", skin);
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Tombol Leaderboard diklik!");
            }
        });

        // Tombol 3: Exit
        TextButton exitButton = new TextButton("EXIT", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(playButton).padBottom(20).width(240).height(50);
        table.row();
        table.add(leaderboardButton).padBottom(20).width(240).height(50);
        table.row();
        table.add(exitButton).width(240).height(50);

        table.center();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.12f, 0.18f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        buttonTexture.dispose();
        buttonHoverTexture.dispose();
    }
}
