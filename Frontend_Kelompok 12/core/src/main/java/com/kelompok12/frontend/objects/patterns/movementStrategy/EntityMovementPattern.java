package com.kelompok12.frontend.objects.patterns.movementStrategy;

import com.kelompok12.frontend.objects.GameObject;

public interface EntityMovementPattern {
    void move(GameObject entity, float delta);
}
