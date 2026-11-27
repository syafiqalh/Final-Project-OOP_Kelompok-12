
package com.kelompok12.frontend.objects.patterns.movementStrategy;

import com.kelompok12.frontend.objects.GameObject;
import com.kelompok12.frontend.objects.Player;

public class BuzzerGerak implements EntityMovementPattern {
    private Player target;
    private float speed;
    private float stopDistance;

    public BuzzerGerak(Player target, float speed, float stopDistance) {
        this.target = target;
        this.speed = speed;
        this.stopDistance = stopDistance;
    }

    @Override
    public void move(GameObject entity, float delta) {
        if (entity == null || !entity.isActive() || target == null || !target.isAlive() || delta <= 0f) {
            return;
        }

        float entityX = entity.getX() + entity.getWidth() / 2f;
        float entityY = entity.getY() + entity.getHeight() / 2f;
        float targetX = target.getX() + target.getWidth() / 2f;
        float targetY = target.getY() + target.getHeight() / 2f;
        float dx = targetX - entityX;
        float dy = targetY - entityY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance <= stopDistance) {
            return;
        }
        float step = Math.min(speed * delta, distance - stopDistance);
        entity.setX(entity.getX() + (dx / distance) * step);
        entity.setY(entity.getY() + (dy / distance) * step);
    }
}
