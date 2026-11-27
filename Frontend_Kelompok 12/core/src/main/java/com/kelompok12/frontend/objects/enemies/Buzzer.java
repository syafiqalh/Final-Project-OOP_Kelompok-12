package com.kelompok12.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.patterns.EnemyAttackPattern;
import com.kelompok12.frontend.objects.patterns.attackStrategy.BuzzerAttack;
import com.kelompok12.frontend.objects.patterns.movementStrategy.BuzzerGerak;
import com.kelompok12.frontend.objects.patterns.movementStrategy.EntityMovementPattern;
import com.kelompok12.frontend.systems.AssetManager;

public class Buzzer extends Enemy {
    private Player target;
    private EntityMovementPattern movementPattern;
    private EnemyAttackPattern attackPattern;
    private String currentDir = "down";

    public Buzzer(String name, int hp, float x, float y, Player target) {
        super(x, y, 62f, 89f, Color.RED, name, hp);
        setSpriteSize(80f, 92f);
        this.target = target;
        this.movementPattern = new BuzzerGerak(target, speed, 55f);
        this.attackPattern = new BuzzerAttack(57f, 10, 0.75f);
    }

    @Override
    public void update(float delta) {
        if (!isAlive()) {
            return;
        }
        super.update(delta);
        float oldX = getX();
        float oldY = getY();
        boolean wasAttacking = attackPattern.isAttacking();
        attackPattern.update(this, target, delta);
        if (!attackPattern.isAttacking()) {
            movementPattern.move(this, delta);
        }
        if (attackPattern.isAttacking()) {
            if (!wasAttacking) {
                float dx = target.getX() + target.getWidth() / 2f - (getX() + getWidth() / 2f);
                float dy = target.getY() + target.getHeight() / 2f - (getY() + getHeight() / 2f);
                updateDirection(dx, dy);
            }
            updateAttackAnimation();
        } else {
            float dx = getX() - oldX;
            float dy = getY() - oldY;
            updateAnimationState(dx, dy, delta);
        }
    }

    private void updateDirection(float dx, float dy) {
        if (dx == 0f && dy == 0f) {
            return;
        }
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0f) {
                currentDir = "right";
            } else {
                currentDir = "left";
            }
        } else {
            if (dy > 0f) {
                currentDir = "up";
            } else {
                currentDir = "down";
            }
        }
    }

    public void updateAnimationState(float dx, float dy, float delta) {
        AssetManager assets = AssetManager.getInstance();

        if (dx != 0f || dy != 0f) {
            updateDirection(dx, dy);
            Animation<TextureRegion> anim =
                assets.getAnimation("buzzer_" + currentDir);
            if (anim != null) {
                setAnimation(anim);
                stateTime += delta;
            }
        } else {
            setAnimation(null);
            TextureRegion idleSprite = assets.getTextureRegion("buzzer_" + currentDir);
            setSprite(idleSprite);
            stateTime = 0f;
        }
    }

    private void updateAttackAnimation() {
        AssetManager assets = AssetManager.getInstance();
        Animation<TextureRegion> anim = assets.getAnimation("buzzer_attack_" + currentDir);
        if (anim != null) {
            setAnimation(anim);
            setStateTime(attackPattern.getAttackTime());
        }
    }
}
