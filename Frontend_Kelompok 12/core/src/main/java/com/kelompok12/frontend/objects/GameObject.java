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
    protected float spriteWidth; //just in case kalo ditanya, Buzzer gede karena biar sprite nyerang cukup
    protected float spriteHeight; // Aslinya 62 x 89
    protected float speed;
    protected Color color;
    protected int damage;
    protected boolean active = true;
    protected TextureRegion sprite;
    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteWidth = width;
        this.spriteHeight = height;
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
        this.spriteWidth = width;
        this.spriteHeight = height;
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
        if (batch != null && active) {
            TextureRegion currentFrame = null;
            if (animation != null) {
                currentFrame = animation.getKeyFrame(stateTime);
            } else if (sprite != null) {
                currentFrame = sprite;
            }
            if (currentFrame != null) {
                float drawX = x - Math.round((spriteWidth - width) / 2f);
                float drawY = y - Math.round((spriteHeight - height) / 2f); //Biar sprite ada di tengah karena kegedean
                batch.draw(currentFrame, drawX, drawY, spriteWidth, spriteHeight);
            }
        }
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        if (this.x < -100 || this.x > screenWidth + 100 || this.y < -100 || this.y > screenHeight + 100) {
            return true;
        }
        else return false;
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

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    public TextureRegion getSprite() {
        return this.sprite;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        if (this.animation != animation) {
            this.animation = animation;
            this.stateTime = 0f;
        }
    }

    public Animation<TextureRegion> getAnimation() {
        return this.animation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void setSpriteSize(float width, float height) {
        if (width > 0f && height > 0f) {
            this.spriteWidth = width;
            this.spriteHeight = height;
        }
    }
}

