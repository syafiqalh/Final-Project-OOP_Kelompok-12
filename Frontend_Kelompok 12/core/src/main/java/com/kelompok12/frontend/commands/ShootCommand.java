package com.kelompok12.frontend.commands;

import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.systems.ProjectileManager;

public class ShootCommand implements Command {

    private float targetX;
    private float targetY;

    public void setTarget(float targetX, float targetY) {   //buat ngeset posisi target tembakannya
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void execute(Player player, ProjectileManager projectileManager) {
        if (player != null && player.isActive() && projectileManager != null) {
            player.shoot(projectileManager, targetX, targetY);
        }
    }
}
