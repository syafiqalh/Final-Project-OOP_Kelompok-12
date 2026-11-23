package com.kelompok12.frontend.commands;

import com.kelompok12.frontend.objects.Player;
import com.kelompok12.frontend.systems.ProjectileManager;

public interface Command {
    void execute(Player player, ProjectileManager projectileManager);
}
