package com.kelompok12.frontend.objects.patterns;

import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.objects.enemies.Enemy;

public interface EnemyAttackPattern {
    void update(Enemy enemy, Player player, float delta);

    boolean isAttacking();
    float getAttackTime();
}
