package com.kelompok12.frontend.commands;

import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.systems.ProjectileManager;

public class MoveCommand implements Command {
    private float dx;
    private float dy;

    public MoveCommand(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void set(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute(Player player, ProjectileManager projectileManager) {
        if (player != null && player.isActive()) {
            player.move(dx, dy);
        }
    }
}
