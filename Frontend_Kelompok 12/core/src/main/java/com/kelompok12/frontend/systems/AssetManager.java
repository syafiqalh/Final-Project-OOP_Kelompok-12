package com.kelompok12.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static AssetManager instance;
    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    private AssetManager() {
        textureRegionMap = new HashMap<>();
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        if (textureMap.containsKey(filename)) {
            return textureMap.get(filename);
        }
        if (Gdx.files != null && Gdx.files.internal(filename).exists()) {
            Texture texture = new Texture(Gdx.files.internal(filename));
            textureMap.put(filename, texture);
            return texture;
        }
        return null;
    }

    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    public void registerRegion(String key, String filename) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            textureRegionMap.put(key, new TextureRegion(tex));
        }
    }

    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                frames[i] = grid[row][startCol + i];
            }
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);
            animationMap.put(key, anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration, boolean flipX, boolean flipY) {
        registerFlippedAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP, flipX, flipY);
    }

    public void registerFlippedAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode, boolean flipX, boolean flipY) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight
            );
            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                TextureRegion frame = new TextureRegion(grid[row][startCol + i]);
                frame.flip(flipX, flipY);
                frames[i] = frame;
            }
            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);
            animationMap.put(key, anim);
            textureRegionMap.put(key, frames[0]);
        }
    }

    public TextureRegion getTextureRegion(String key) {
        return textureRegionMap.get(key);
    }

    public TextureRegion getRegion(String key) {
        return getTextureRegion(key);
    }

    public Animation<TextureRegion> getAnimation(String key) {
        return animationMap.get(key);
    }

    public void init() {
        registerAnimationFromSheet("buzzer_down", "Buzzer.png", 80, 92, 0, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("buzzer_up", "Buzzer.png", 80, 92, 1, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("buzzer_right", "Buzzer.png", 80, 92, 2, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("buzzer_left", "Buzzer.png", 80, 92, 3, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);

        registerAnimationFromSheet("satgas_down", "Satgas.png", 64, 91, 0, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("satgas_up", "Satgas.png", 64, 91, 1, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("satgas_right", "Satgas.png", 64, 91, 2, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("satgas_left", "Satgas.png", 64, 91, 3, 0, 4, 1f / 12f, Animation.PlayMode.LOOP);

        registerAnimationFromSheet("buzzer_attack_down", "Buzzer.png", 80, 92, 4, 0, 3, 1f / 12f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("buzzer_attack_up", "Buzzer.png", 80, 92, 5, 0, 3, 1f / 12f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("buzzer_attack_right", "Buzzer.png", 80, 92, 6, 0, 3, 1f / 12f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("buzzer_attack_left", "Buzzer.png", 80, 92, 7, 0, 3, 1f / 12f, Animation.PlayMode.NORMAL);

        registerAnimationFromSheet("satgas_attack_down", "Satgas.png", 64, 91, 4, 0, 2, 1f / 12f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("satgas_attack_up", "Satgas.png", 64, 91, 5, 0, 2, 1f / 12f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("satgas_attack_right", "Satgas.png", 64, 91, 6, 0, 2, 1f / 12f, Animation.PlayMode.NORMAL);
        registerAnimationFromSheet("satgas_attack_left", "Satgas.png", 64, 91, 7, 0, 2, 1f / 12f, Animation.PlayMode.NORMAL);

        registerRegion("bullet_player", "bullet_player.png");
    }

    public void dispose() {
        for (Texture texture : textureMap.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }

        textureMap.clear();
        textureRegionMap.clear();
        animationMap.clear();
    }
}
