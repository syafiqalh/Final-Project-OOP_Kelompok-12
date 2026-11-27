package com.kelompok12.frontend.objects.patterns.attackStrategy;

import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Enemy;
import com.kelompok12.frontend.objects.patterns.EnemyAttackPattern;

public class BuzzerAttack implements EnemyAttackPattern {
    private float FRAME_DURATION = 1f / 12f;
    private int HIT_FRAME = 2;
    private int TOTAL_FRAMES = 3;
    private float attackRange;
    private int damage;
    private float cooldownDuration;
    private float attackTimer = 0f;
    private float cooldownTimer = 0f;
    private boolean attacking = false;
    private boolean hitRegistered = false;

    public BuzzerAttack(float attackRange, int damage, float cooldownDuration) {
        this.attackRange = attackRange;
        this.damage = damage;
        this.cooldownDuration = cooldownDuration;
    }

    @Override
    public void update(Enemy enemy, Player player, float delta) {
        if (enemy == null || !enemy.isAlive()
            || player == null || !player.isAlive()
            || delta <= 0f) {
            return;
        }
        if (cooldownTimer > 0f) {
            cooldownTimer = Math.max(0f, cooldownTimer - delta);
        }
        if (!attacking) {
            if (cooldownTimer > 0f || !inRange(enemy, player)) {
                return;
            }
            attacking = true;
            attackTimer = 0f;
            hitRegistered = false;
        }
        attackTimer += delta;
        int currentFrame = Math.min(
            (int) (attackTimer / FRAME_DURATION),
            TOTAL_FRAMES - 1
        );
        if (currentFrame >= HIT_FRAME && !hitRegistered) {
            hitRegistered = true;
            if (inRange(enemy, player)) {
                player.takeDamage(damage);
                System.out.println("Buzzer memukul. HP: " + player.getHp());
            }
        }
        if (attackTimer >= TOTAL_FRAMES * FRAME_DURATION) {
            attacking = false;
            cooldownTimer = cooldownDuration;
        }
    }

    private boolean inRange(Enemy enemy, Player player) {
        float enemyX = enemy.getX() + enemy.getWidth() / 2f;
        float enemyY = enemy.getY() + enemy.getHeight() / 2f;
        float playerX = player.getX() + player.getWidth() / 2f;
        float playerY = player.getY() + player.getHeight() / 2f;
        float dx = playerX - enemyX;
        float dy = playerY - enemyY;
        return dx * dx + dy * dy <= attackRange * attackRange;
    }

    @Override
    public boolean isAttacking() {
        return attacking;
    }

    @Override
    public float getAttackTime() {
        return attackTimer;
    }
}
