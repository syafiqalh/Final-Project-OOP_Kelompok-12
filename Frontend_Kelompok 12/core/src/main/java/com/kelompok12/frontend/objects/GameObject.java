package com.kelompok12.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public abstract class GameObject implements Collidable{
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;
    protected int damage;
    protected boolean active = true;
    protected TextureRegion sprite;
    protected Animation<TextureRegion> animation;

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    public void destroy() {
        active = false;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public void setHitbox(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onCollision(Collidable other) {
        // Test only
        System.out.println(this + "collides with" + other);
    }

    public void update(float delta) {
    }

    // Render pakai kotak
    public void render(ShapeRenderer shapeRenderer) {
        if (shapeRenderer != null && color != null && active) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    // Render pakai sprite
    public void render(SpriteBatch batch) {
        // Nanti saja
    }


    public boolean isActive() {
        return active;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}

